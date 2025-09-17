package com.kangwon.festival.domain.guestbook.repository;

import com.kangwon.festival.global.entity.GuestbookInfo;
import com.kangwon.festival.global.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GuestbookRepository extends JpaRepository<GuestbookInfo, Long> {
    // 전체 방명록 조회
    List<GuestbookInfo> findByGuestbookIsDeletedFalseOrderByCreatedDateTimeDesc();

    // 특정 방명록 조회
    Optional<GuestbookInfo> findByGuestbookIdAndGuestbookIsDeletedFalse(Long guestbookId);

    // 특정 사용자가 작성한 방명록 조회
    List<GuestbookInfo> findByUserAndGuestbookIsDeletedFalseOrderByCreatedDateTimeDesc(UserInfo user);
}
