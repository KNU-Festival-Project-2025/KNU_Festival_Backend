package com.kangwon.festival.main.mypage.repository;

import com.kangwon.festival.main.mypage.domain.GuestbookInfo;
import com.kangwon.festival.main.mypage.domain.UserBlock;
import com.kangwon.festival.main.mypage.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserBlockRepository extends JpaRepository<UserBlock, Integer> {
    List<UserBlock> findByBlocker(UserInfo blocker);
    void deleteByBlockerAndBlocked(UserInfo blocker, UserInfo blocked);
}
