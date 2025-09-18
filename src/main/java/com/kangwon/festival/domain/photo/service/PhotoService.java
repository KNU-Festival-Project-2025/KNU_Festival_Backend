package com.kangwon.festival.domain.photo.service;

import com.kangwon.festival.domain.photo.dto.PhotoRequest;
import com.kangwon.festival.domain.photo.dto.PhotoResponse;
import com.kangwon.festival.domain.photo.entity.Photo;
import com.kangwon.festival.domain.photo.exception.DatabaseDeleteException;
import com.kangwon.festival.domain.photo.exception.ForbiddenPhotoDeleteException;
import com.kangwon.festival.domain.photo.exception.InvalidFileTypeException;
import com.kangwon.festival.domain.photo.exception.NotFoundPhotoException;
import com.kangwon.festival.domain.photo.exception.PhotoLimitExceededException;
import com.kangwon.festival.domain.photo.exception.StorageDeleteException;
import com.kangwon.festival.domain.photo.exception.UploadFileException;
import com.kangwon.festival.domain.photo.repository.PhotoRepository;
import com.kangwon.festival.domain.security.dto.CustomUserDetails;
import com.kangwon.festival.domain.user.entity.User;
import com.kangwon.festival.domain.user.exception.DuplicateNicknameException;
import com.kangwon.festival.domain.user.exception.NotFoundUserException;
import com.kangwon.festival.global.annotation.MethodDescription;
import com.kangwon.festival.global.dto.ApiResponseData;
import com.kangwon.festival.global.dto.ApiResponseMessage;
import jakarta.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class PhotoService {
    private final PhotoRepository photoRepository;
    private final GcsService gcsService;

    @MethodDescription(description = "사진 게시글을 생성합니다.")
    public ApiResponseMessage createPhoto(CustomUserDetails customUser, PhotoRequest request, MultipartFile file) {
        if (file == null || file.isEmpty()) throw new InvalidFileTypeException();

        User user = findUser(customUser);

        long photoCount = photoRepository.countByUser(user);
        if (photoCount >= 5) {
            throw new PhotoLimitExceededException();
        }

        String fileUrl = gcsService.upload("photoImage", file);
        try {
            Photo photo = createPhoto(user, request, fileUrl, file);
            photoRepository.save(photo);

            return ApiResponseMessage.of("사진 게시글 저장에 성공하였습니다.");
        } catch (Exception e) {
            gcsService.deleteByUrl(fileUrl);
            throw new UploadFileException();
        }
    }

    @MethodDescription(description = "사진 게시글을 삭제합니다. (본인만 가능)")
    public ApiResponseMessage deletePhoto(CustomUserDetails customUser, Long photoId) {
        Photo photo = photoFindById(photoId);
        Long currentUserId = Long.valueOf(findUser(customUser).getId());

        if (!photo.getUser().getId().equals(currentUserId)) {
            throw new ForbiddenPhotoDeleteException();
        }
        try {
            photoRepository.delete(photo);
            photoRepository.flush();

            gcsService.deleteByUrl(photo.getImgUrl());
            return ApiResponseMessage.of("사진 게시글 삭제에 성공하였습니다.");
        } catch (DataAccessException | PersistenceException e) {
            throw new DatabaseDeleteException();
        } catch (Exception e) {
            throw new StorageDeleteException();
        }
    }

    @MethodDescription(description = "사진 게시글 전체 목록을 조회합니다.")
    @Transactional(readOnly = true)
    public ApiResponseData getPhotos(CustomUserDetails customUser) {
        Long currentUserId = Long.valueOf(findUser(customUser).getId());
        List<Photo> photos = photoRepository.findAllByOrderByCreatedDateTimeDesc();
        List<PhotoResponse> items = photos.stream()
                .map(photo -> PhotoResponse.of(photo, currentUserId))
                .toList();
        return ApiResponseData.of(items,"사진 게시글 조회에 성공하였습니다.");
    }

    @MethodDescription(description = "사진 게시글 단일 조회합니다.")
    @Transactional(readOnly = true)
    public ApiResponseData getPhoto(CustomUserDetails customUser, Long photoId) {
        Photo photo = photoFindById(photoId);
        Long currentUserId = Long.valueOf(findUser(customUser).getId());

        PhotoResponse response = PhotoResponse.of(photo, currentUserId);
        return ApiResponseData.of(response, "사진 게시글 조회에 성공하였습니다.");
    }

    @MethodDescription(description = "photoId를 통해 entity 를 반환합니다.")
    private Photo photoFindById(Long photoId) {
        return photoRepository.findById(photoId).orElseThrow(NotFoundPhotoException::new);
    }

    @MethodDescription(description = "유저가 존재하는 지 확인합니다.")
    private User findUser(CustomUserDetails principal) {
        return Optional.ofNullable(principal)
                .map(CustomUserDetails::getUser)
                .orElseThrow(NotFoundUserException::new);
    }

    @MethodDescription(description = "photo 객체를 반환합니다.")
    private Photo createPhoto(User user, PhotoRequest request, String fileUrl, MultipartFile file) {
        return Photo.builder()
                .user(user)
                .nickname(user.getNickname())
                .imgUrl(fileUrl)
                .originImgUrl(file.getOriginalFilename())
                .content(request.content())
                .build();
    }
}
