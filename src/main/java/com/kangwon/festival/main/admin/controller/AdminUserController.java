package com.kangwon.festival.main.admin.controller;

import com.kangwon.festival.global.entity.UserReport;
import com.kangwon.festival.main.admin.dto.UserBlockedResponse;
import com.kangwon.festival.main.admin.dto.UserReportResponse;
import com.kangwon.festival.main.admin.service.AdminUserService;
import com.kangwon.festival.global.entity.UserBlock;
import com.kangwon.festival.global.entity.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class AdminUserController {
    private final AdminUserService adminUserService;

    // 모든 신고 조회
    @GetMapping("/reports")
    public ResponseEntity<List<UserReportResponse>> getAllReports() {
        return ResponseEntity.ok(adminUserService.getAllReports());
    }

    // 신고된 특정 유저 조회
    @GetMapping("/reports/{userId}")
    public ResponseEntity<List<UserReportResponse>> getReportsByUser(@PathVariable int userId) {
        return ResponseEntity.ok(adminUserService.getReportsByUser(userId));
    }


    // 차단 내역 조회
    @GetMapping("/blocked")
    public ResponseEntity<List<UserBlockedResponse>> getAllBlocks() {
        return ResponseEntity.ok(adminUserService.getAllBlocks());
    }

    // 차단된 특정 유저 조회
    @GetMapping("/blocked/{userId}")
    public ResponseEntity<List<UserBlockedResponse>> getBlocksByBlockedUser(@PathVariable int userId) {
        return ResponseEntity.ok(adminUserService.getBlocksByBlockedUser(userId));
    }

    // 유저 전체 조회
    @GetMapping("/userList")
    public ResponseEntity<List<UserInfo>> getAllUser() {
        return ResponseEntity.ok(adminUserService.getAllUser());
    }

    // 유저 조회(차단용)
    @GetMapping("/userList/{userId}")
    public ResponseEntity<UserInfo> getUser(@PathVariable int userId) {
        return ResponseEntity.ok(adminUserService.getUser(userId));
    }


    // 유처 이용 차단하기
    @PatchMapping("/userList/{userId}/ban")
    public ResponseEntity<String> banUser(@PathVariable int userId) {
        adminUserService.banUser(userId);
        return ResponseEntity.ok("차단이 완료되었습니다.");
    }

    // 유저 이용 차단 해제하기

}
