package com.kangwon.festival.main.admin.repository;

import com.kangwon.festival.global.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminUserInfoRepository extends JpaRepository<UserInfo, Integer> {

}
