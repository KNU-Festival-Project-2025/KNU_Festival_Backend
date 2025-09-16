package com.kangwon.festival.domain.photo.service;

import com.kangwon.festival.domain.photo.entity.Photo;
import com.kangwon.festival.domain.photo.entity.PhotoLike;
import com.kangwon.festival.domain.photo.exception.NotFoundPhotoException;
import com.kangwon.festival.domain.photo.repository.PhotoLikeRepository;
import com.kangwon.festival.domain.photo.repository.PhotoRepository;
import com.kangwon.festival.domain.security.dto.CustomUserDetails;
import com.kangwon.festival.domain.user.entity.User;
import com.kangwon.festival.domain.user.exception.NotFoundUserException;
import com.kangwon.festival.domain.user.respository.UserRepository;
import com.kangwon.festival.global.annotation.MethodDescription;
import com.kangwon.festival.global.dto.ApiResponseMessage;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PhotoLikeService {

    private final PhotoRepository photoRepository;
    private final UserRepository userRepository;
    private final PhotoLikeRepository photoLikeRepository;

    public ApiResponseMessage toggleLike(CustomUserDetails customUser, Long photoId) {
        User user = findUser(customUser);
        Long userId = Long.valueOf(user.getId());
        photoFindById(photoId);

        if (photoLikeRepository.existsByPhotoIdAndUserId(photoId, userId)) {
            PhotoLike like = photoLikeRepository.findByPhotoIdAndUserId(photoId, userId).orElseThrow();
            photoLikeRepository.delete(like);
            photoRepository.decreaseLikeCount(photoId);
            return ApiResponseMessage.of("좋아요가 취소되었습니다.");
        } else {
            saveLike(photoId, userId);
            photoRepository.increaseLikeCount(photoId);
            return ApiResponseMessage.of("좋아요가 정상적으로 처리되었습니다.");
        }
    }

    @MethodDescription(description = "PhotoLike 저장 로직")
    private void saveLike(Long photoId, Long userId) {
        Photo photoRef = photoRepository.getReferenceById(photoId);
        User userRef = userRepository.getReferenceById(userId);

        PhotoLike photoLike = PhotoLike.builder()
                .photo(photoRef)
                .user(userRef)
                .build();
        photoLikeRepository.save(photoLike);
    }

    @MethodDescription(description = "게시글이 있는 지 확인합니다.")
    private void photoFindById(Long photoId) {
        photoRepository.findById(photoId).orElseThrow(NotFoundPhotoException::new);
    }

    @MethodDescription(description = "유저가 존재하는 지 확인합니다.")
    private User findUser(CustomUserDetails principal) {
        return Optional.ofNullable(principal)
                .map(CustomUserDetails::getUser)
                .orElseThrow(NotFoundUserException::new);
    }
}