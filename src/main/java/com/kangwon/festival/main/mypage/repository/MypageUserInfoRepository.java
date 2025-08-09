package com.kangwon.festival.main.mypage.repository;


import com.kangwon.festival.global.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MypageUserInfoRepository extends JpaRepository<UserInfo, Integer> {
    Optional<UserInfo> findByUserNickname(String nickname);
}
