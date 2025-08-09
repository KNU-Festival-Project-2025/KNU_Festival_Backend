package com.kangwon.festival.main.admin.repository;

import com.kangwon.festival.global.entity.UserReport;
import com.kangwon.festival.global.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminUserReportRepository extends JpaRepository<UserReport, Integer> {
    List<UserReport> findByReportedUser(UserInfo reportedUser);
}