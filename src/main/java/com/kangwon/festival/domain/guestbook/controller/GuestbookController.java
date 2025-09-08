package com.kangwon.festival.domain.guestbook.controller;

import com.kangwon.festival.domain.guestbook.dto.GuestbookRequest;
import com.kangwon.festival.domain.guestbook.dto.GuestbookResponse;
import com.kangwon.festival.domain.guestbook.service.GuestbookService;
import com.kangwon.festival.global.annotation.UserOnly;
import com.kangwon.festival.global.dto.ApiResponseData;
import com.kangwon.festival.global.dto.ApiResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/guestbooks")
@RequiredArgsConstructor
public class GuestbookController {

    private final GuestbookService guestbookService;

    // 방명록 등록
    @UserOnly
    @PostMapping("/register")
    public ResponseEntity<ApiResponseData<GuestbookResponse>> create(Authentication authentication, @RequestBody GuestbookRequest req) {
        Long userId = (Long) authentication.getPrincipal();
        GuestbookResponse body = guestbookService.create(userId, req);
        URI location = URI.create("/api/guestbooks/" + body.getGuestbookId());

        return ResponseEntity
                .created(location)
                .body(ApiResponseData.of(body, "방명록이 등록되었습니다."));
    }

    // 방명록 전체 조회
    @GetMapping
    public ResponseEntity<ApiResponseData<List<GuestbookResponse>>> getAll() {
        List<GuestbookResponse> list = guestbookService.getAllGuestbooks();
        return ResponseEntity.ok(ApiResponseData.of(list, "방명록 목록을 조회했습니다."));
    }

    // 방명록 삭제
    @UserOnly
    @DeleteMapping("/{guestbookId}")
    public ResponseEntity<ApiResponseMessage> delete(Authentication authentication,
                                                     @PathVariable Long guestbookId) {
        Long userId = (Long) authentication.getPrincipal();
        guestbookService.deleteMyGuestbook(userId, guestbookId);
        return ResponseEntity.ok(ApiResponseMessage.of("방명록이 삭제되었습니다."));
    }


    /*
    // 특정 방명록 조회
    @GetMapping("/{guestbookId}")
    public ResponseEntity<ApiResponseData<GuestbookResponse>> getById(@PathVariable Long guestbookId) {
        GuestbookResponse body = guestbookService.getById(guestbookId);
        return ResponseEntity.ok(ApiResponseData.of(body, "방명록을 조회했습니다."));
    }


    //내 방명록 조회
    @GetMapping("/mine")
    public ResponseEntity<ApiResponseData<List<GuestbookResponse>>> getMine(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        List<GuestbookResponse> list = guestbookService.getMyGuestbooks(userId);
        return ResponseEntity.ok(ApiResponseData.of(list, "내 방명록을 조회했습니다."));
    }


    // 내가 쓴 방명록 수정
    @PatchMapping("/{guestbookId}")
    public ResponseEntity<ApiResponseData<GuestbookResponse>> update(Authentication authentication,
                                                    @PathVariable Long guestbookId,
                                                    @RequestBody GuestbookRequest req) {
        Long userId = (Long) authentication.getPrincipal();
        GuestbookResponse body = guestbookService.updateMyGuestbook(userId, guestbookId, req);
        return ResponseEntity.ok(ApiResponseData.of(body, "방명록이 수정되었습니다."));
    }
*/



}
