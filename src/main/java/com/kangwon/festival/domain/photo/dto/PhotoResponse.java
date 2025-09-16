package com.kangwon.festival.domain.photo.dto;

import com.kangwon.festival.domain.photo.entity.Photo;
import java.time.LocalDateTime;
import lombok.Builder;
@Builder
public record PhotoResponse(
        Long id,
        String photoNickname,
        String nickname,
        String imgUrl,
        String content,
        long likeCount,
        boolean deletable,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static PhotoResponse from(Photo photo) {
        return PhotoResponse.builder()
                .id(photo.getId())
                .photoNickname(photo.getPhotoNickname())
                .nickname(photo.getNickname())
                .imgUrl(photo.getImgUrl())
                .content(photo.getContent())
                .likeCount(photo.getLikeCount())
                .createdAt(photo.getCreatedDateTime())
                .updatedAt(photo.getModifiedDateTime())
                .build();
    }
    public static PhotoResponse of(Photo photo, Long currentUserId) {
        boolean isDeletable = photo.getUser() != null && photo.getUser().getId().equals(currentUserId);
        return PhotoResponse.builder()
                .id(photo.getId())
                .photoNickname(photo.getPhotoNickname())
                .nickname(photo.getNickname())
                .imgUrl(photo.getImgUrl())
                .content(photo.getContent())
                .likeCount(photo.getLikeCount())
                .createdAt(photo.getCreatedDateTime())
                .updatedAt(photo.getModifiedDateTime())
                .deletable(isDeletable)
                .build();
    }
}
