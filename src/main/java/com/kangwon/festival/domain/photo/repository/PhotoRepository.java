package com.kangwon.festival.domain.photo.repository;

import com.kangwon.festival.domain.photo.entity.Photo;
import com.kangwon.festival.domain.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update Photo p set p.likeCount = p.likeCount + 1 where p.id = :photoId")
    int increaseLikeCount(@Param("photoId") Long photoId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update Photo p set p.likeCount = case when p.likeCount > 0 then p.likeCount - 1 else 0 end where p.id = :photoId")
    int decreaseLikeCount(@Param("photoId") Long photoId);

    List<Photo> findAllByOrderByLikeCountDesc();
    List<Photo> findAllByOrderByCreatedDateTimeDesc();
    long countByUser(User user);
}
