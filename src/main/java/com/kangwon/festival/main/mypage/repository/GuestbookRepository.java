package com.kangwon.festival.main.mypage.repository;

import com.kangwon.festival.main.mypage.domain.GuestbookInfo;
import com.kangwon.festival.main.mypage.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuestbookRepository extends JpaRepository<GuestbookInfo, Integer> {
    List<GuestbookInfo> findByUser(UserInfo user);
}
