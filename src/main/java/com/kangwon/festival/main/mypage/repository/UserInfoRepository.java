package com.kangwon.festival.main.mypage.repository;


import com.kangwon.festival.main.mypage.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    Optional<UserInfo> findByUserNickname(String nickname);
}
