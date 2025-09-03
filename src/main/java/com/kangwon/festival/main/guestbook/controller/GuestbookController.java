package com.kangwon.festival.main.guestbook.controller;

import com.kangwon.festival.global.annotation.MethodDescription;
import com.kangwon.festival.main.guestbook.dto.GuestbookRequest;
import com.kangwon.festival.main.guestbook.dto.GuestbookResponse;
import com.kangwon.festival.main.guestbook.service.GuestbookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/guestbooks")
@RequiredArgsConstructor
public class GuestbookController {

    private final GuestbookService guestbookService;

    // 방명록 등록
    @PostMapping("/register")
    public ResponseEntity<GuestbookResponse> create(@RequestBody GuestbookRequest req) {
        return ResponseEntity.ok(guestbookService.create(req));
    }

    // 방명록 전체 조회
    @GetMapping
    public ResponseEntity<List<GuestbookResponse>> getAll() {
        return ResponseEntity.ok(guestbookService.getAllGuestbooks());
    }

    // 특정 방명록 조회
    @GetMapping("/{guestbookId}")
    public ResponseEntity<GuestbookResponse> getById(@PathVariable Long guestbookId) {
        return ResponseEntity.ok(guestbookService.getById(guestbookId));
    }

    //내 방명록 조회
    @GetMapping("/mine")
    public ResponseEntity<List<GuestbookResponse>> getMine(@RequestParam("userId") Long userId) {
        return ResponseEntity.ok(guestbookService.getMyGuestbooks(userId));
    }

    // 내가 쓴 방명록 수정
    @PatchMapping("/{guestbookId}")
    public ResponseEntity<GuestbookResponse> update(@RequestParam("userId") Long userId,
                                                    @PathVariable Long guestbookId,
                                                    @RequestBody GuestbookRequest req) {
        return ResponseEntity.ok(guestbookService.updateMyGuestbook(userId, guestbookId, req));
    }

    // 방명록 삭제
    @DeleteMapping("/{guestbookId}")
    public ResponseEntity<?> delete(@RequestParam("userId") Long userId,
                                       @PathVariable Long guestbookId) {
        guestbookService.deleteMyGuestbook(userId, guestbookId);
        return ResponseEntity.noContent().build();
//        return ResponseEntity.ok("방명록이 삭제되었습니다.");
    }

}
