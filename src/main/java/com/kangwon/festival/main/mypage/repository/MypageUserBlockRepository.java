package com.kangwon.festival.main.mypage.repository;

import com.kangwon.festival.global.entity.UserBlock;
import com.kangwon.festival.global.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MypageUserBlockRepository extends JpaRepository<UserBlock, Integer> {
    List<UserBlock> findByBlocker(UserInfo blocker);
    void deleteByBlockerAndBlocked(UserInfo blocker, UserInfo blocked);
}
