package com.kangwon.festival.main.mypage.repository;

import com.kangwon.festival.global.entity.GuestbookInfo;
import com.kangwon.festival.global.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MypageGuestbookRepository extends JpaRepository<GuestbookInfo, Integer> {
    List<GuestbookInfo> findByUser(UserInfo user);
}
