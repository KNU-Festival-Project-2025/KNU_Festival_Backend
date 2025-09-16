package com.kangwon.festival.domain.photo.controller;

import com.kangwon.festival.domain.photo.service.PhotoLikeService;
import com.kangwon.festival.domain.security.dto.CustomUserDetails;
import com.kangwon.festival.global.annotation.CurrentUser;
import com.kangwon.festival.global.dto.ApiResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/photo/like")
public class PhotoLikeController {
    private final PhotoLikeService photoLikeService;
    @PostMapping("/{photoId}")
    public ResponseEntity<ApiResponseMessage> toggleLike(@CurrentUser CustomUserDetails user, @PathVariable Long photoId) {
        return ResponseEntity.ok(photoLikeService.toggleLike(user, photoId));
    }
}
