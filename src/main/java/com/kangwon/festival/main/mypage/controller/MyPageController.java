package com.kangwon.festival.main.mypage.controller;

import com.kangwon.festival.global.annotation.MethodDescription;
import com.kangwon.festival.main.mypage.dto.GuestbookResponse;
import com.kangwon.festival.main.mypage.dto.UserBlockResponse;
import com.kangwon.festival.main.mypage.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @MethodDescription(description = "닉네임 변경")
    @PatchMapping("/nickname")
    public ResponseEntity<?> changeNickname(@RequestParam int userId, @RequestParam String newNickname) {
        myPageService.updateNickname(userId, newNickname);
        return ResponseEntity.ok("닉네임이 변경되었습니다.");
    }

    @MethodDescription(description = "회원 탈퇴")
    @DeleteMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestParam int userId) {
        myPageService.deleteUser(userId);
        return ResponseEntity.ok("회원 탈퇴 처리되었습니다.");
    }

    @MethodDescription(description = "내 방명록 글 조회")
    @GetMapping("/myguestbook")
    public ResponseEntity<List<GuestbookResponse>> guestbooks(@RequestParam int userId) {
        return ResponseEntity.ok(myPageService.getMyGuestbook(userId));
    }

    @MethodDescription(description = "차단 내역 확인")
    @GetMapping("/block")
    public ResponseEntity<List<UserBlockResponse>> blockedList(@RequestParam int userId) {
        return ResponseEntity.ok(myPageService.getBlockedUsers(userId));
    }

    @MethodDescription(description = "차단 해제")
    @DeleteMapping("/unblock")
    public ResponseEntity<?> unblock(@RequestParam int blockerId, @RequestParam int blockedId) {
        myPageService.unblockUser(blockerId, blockedId);
        return ResponseEntity.ok("차단이 해제되었습니다.");
    }

}
