package com.kangwon.festival.domain.photo.repository;

import com.kangwon.festival.domain.photo.entity.PhotoLike;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoLikeRepository extends JpaRepository<PhotoLike, Long> {
    boolean existsByPhotoIdAndUserId(Long photoId, Long userId);
    Optional<PhotoLike> findByPhotoIdAndUserId(Long photoId, Long userId);
}
