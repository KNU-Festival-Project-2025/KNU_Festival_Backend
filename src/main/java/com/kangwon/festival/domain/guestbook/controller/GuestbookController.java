package com.kangwon.festival.domain.guestbook.controller;

import com.kangwon.festival.domain.guestbook.dto.GuestbookRequest;
import com.kangwon.festival.domain.guestbook.dto.GuestbookResponse;
import com.kangwon.festival.domain.guestbook.service.GuestbookService;
import com.kangwon.festival.domain.security.dto.CustomUserDetails;
import com.kangwon.festival.global.annotation.CurrentUser;
import com.kangwon.festival.global.annotation.UserOnly;
import com.kangwon.festival.global.dto.ApiResponseData;
import com.kangwon.festival.global.dto.swagger.ApiResponseDataOfGuestbookList;
import com.kangwon.festival.global.dto.swagger.ApiResponseDataOfGuestbookResponse;
import com.kangwon.festival.global.dto.ApiResponseMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/guestbooks")
@RequiredArgsConstructor
@Tag(name = "Guestbook", description = "방명록 API")
@SecurityRequirement(name = "accessTokenHeader")
public class GuestbookController {

    private final GuestbookService guestbookService;

    // 방명록 등록
    @Operation(
            summary = "방명록 등록",
            description = "X-Access-Token 헤더로 인증 후 방명록을 등록합니다."
    )
    @ApiResponse(
            responseCode = "201",
            description = "등록 성공",
            content = @Content(
                    schema = @Schema(implementation = ApiResponseDataOfGuestbookResponse.class)
            ),
            headers = {
                    @Header(name = "Location", description = "생성된 리소스 URI", schema = @Schema(type = "string"))
            }
    )
    @ApiResponse(
            responseCode = "401",
            description = "유효하지 않은 토큰",
            content = @Content(schema = @Schema(implementation = ApiResponseMessage.class))
    )
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
    @Operation(summary = "방명록 조회")
    @ApiResponse(
            responseCode = "200",
            description = "조회 성공",
            content = @Content(schema = @Schema(implementation = ApiResponseDataOfGuestbookList.class))
    )
    public ResponseEntity<ApiResponseData<List<GuestbookResponse>>> getAll() {
        List<GuestbookResponse> list = guestbookService.getAllGuestbooks();
        return ResponseEntity.ok(ApiResponseData.of(list, "방명록 목록을 조회했습니다."));
    }

    // 방명록 삭제

    @UserOnly
    @DeleteMapping("/{guestbookId}")
    @Operation(summary = "내 방명록 삭제")
    @ApiResponse(
            responseCode = "200",
            description = "삭제 성공",
            content = @Content(schema = @Schema(implementation = ApiResponseMessage.class))
    )
    public ResponseEntity<ApiResponseMessage> delete(@CurrentUser CustomUserDetails user, @PathVariable Long guestbookId) {
        Integer userId = user.getUser().getId();
        guestbookService.deleteMyGuestbook(userId, guestbookId);
        return ResponseEntity.ok(ApiResponseMessage.of("방명록이 삭제되었습니다."));
    }

}
