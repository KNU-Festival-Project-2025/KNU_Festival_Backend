package com.kangwon.festival.domain.mypage.controller;

import com.kangwon.festival.domain.mypage.dto.GuestbookResponse;
import com.kangwon.festival.domain.mypage.dto.UserBlockResponse;
import com.kangwon.festival.domain.mypage.service.MyPageService;
import com.kangwon.festival.global.annotation.UserOnly;
import com.kangwon.festival.global.dto.ApiResponseData;
import com.kangwon.festival.global.dto.ApiResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    // 닉네임 변경
    @UserOnly
    @PatchMapping("/nickname")
    public ResponseEntity<ApiResponseMessage> changeNickname(Authentication authentication, @RequestParam String newNickname) {
        Long userId = (Long) authentication.getPrincipal();
        myPageService.updateNickname(userId, newNickname);
        return ResponseEntity.ok(ApiResponseMessage.of("닉네임이 변경되었습니다."));
    }

    // 회원 탈퇴
    @UserOnly
    @DeleteMapping("/withdraw")
    public ResponseEntity<ApiResponseMessage> withdraw(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        myPageService.deleteUser(userId);
        return ResponseEntity.ok(ApiResponseMessage.of("회원 탈퇴 처리되었습니다."));
    }

    // 내 방명록 글 조회
    @UserOnly
    @GetMapping("/myguestbook")
    public ResponseEntity<ApiResponseData<List<GuestbookResponse>>> guestbooks(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        List<GuestbookResponse> list = myPageService.getMyGuestbook(userId);
        return ResponseEntity.ok(ApiResponseData.of(list, "내 방명록을 조회했습니다."));
    }

    //차단 내역 확인
    @UserOnly
    @GetMapping("/block")
    public ResponseEntity<ApiResponseData<List<UserBlockResponse>>> blockedList(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        List<UserBlockResponse> list = myPageService.getBlockedUsers(userId);
        return ResponseEntity.ok(ApiResponseData.of(list, "차단 내역을 조회했습니다."));
    }

    // 차단 해제
    @UserOnly
    @DeleteMapping("/unblock")
    public ResponseEntity<ApiResponseMessage> unblock(Authentication authentication, @RequestParam Long blockedId) {
        Long blockerId = (Long) authentication.getPrincipal();
        myPageService.unblockUser(blockerId, blockedId);
        return ResponseEntity.ok(ApiResponseMessage.of("차단이 해제되었습니다."));
    }

}
