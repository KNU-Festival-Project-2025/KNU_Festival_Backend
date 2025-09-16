package com.kangwon.festival.domain.photo.service;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.kangwon.festival.domain.photo.exception.InvalidFileTypeException;
import com.kangwon.festival.domain.photo.exception.UploadFileException;
import com.kangwon.festival.global.annotation.MethodDescription;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class GcsService {

    private final Storage storage;

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    @MethodDescription(description = "GCS에 파일 업로드 후 접근 URL을 반환합니다.")
    public String upload(String dir, MultipartFile file) {
        if (file == null || file.isEmpty()) throw new InvalidFileTypeException();

        String originalName = Objects.requireNonNullElse(file.getOriginalFilename(), "unnamed");
        String blobName = dir + "/" + UUID.randomUUID() + originalName;
        String fileUrl = toFileUrl(blobName);

        try {
            BlobId blobId = BlobId.of(bucketName, blobName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                    .setContentType(file.getContentType())
                    .build();
            storage.create(blobInfo, file.getBytes());
            return fileUrl;
        } catch (Exception e) {
            throw new UploadFileException();
        }
    }

    @MethodDescription(description = "GCS에서 URL에 해당하는 객체를 삭제합니다.")
    public void deleteByUrl(String imageUrl) {
        if (imageUrl == null || imageUrl.isBlank()) return;
        try {
            String blobName = extractBlobNameFromUrl(imageUrl);
            storage.delete(BlobId.of(bucketName, blobName));
        } catch (Exception e) {
            // 정책상 무시. 필요하면 예외 전파로 변경 가능
        }
    }

    private String toFileUrl(String blobName) {
        return "https://storage.googleapis.com/" + bucketName + "/" + blobName;
    }

    private String extractBlobNameFromUrl(String imageUrl) {
        int pos = imageUrl.indexOf(bucketName + "/");
        if (pos < 0) throw new UploadFileException();
        return imageUrl.substring(pos + bucketName.length() + 1);
    }

}