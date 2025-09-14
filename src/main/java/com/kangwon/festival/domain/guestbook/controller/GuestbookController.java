package com.kangwon.festival.domain.guestbook.controller;

import com.kangwon.festival.domain.guestbook.dto.GuestbookRequest;
import com.kangwon.festival.domain.guestbook.dto.GuestbookResponse;
import com.kangwon.festival.domain.guestbook.service.GuestbookService;
import com.kangwon.festival.domain.security.dto.CustomUserDetails;
import com.kangwon.festival.global.annotation.CurrentUser;
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
    public ResponseEntity<ApiResponseData<GuestbookResponse>> create(@CurrentUser CustomUserDetails user, @RequestBody GuestbookRequest req) {
        Integer userId = user.getUser().getId();
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
    public ResponseEntity<ApiResponseMessage> delete(@CurrentUser CustomUserDetails user, @PathVariable Long guestbookId) {
        Integer userId = user.getUser().getId();
        guestbookService.deleteMyGuestbook(userId, guestbookId);
        return ResponseEntity.ok(ApiResponseMessage.of("방명록이 삭제되었습니다."));
    }

}
