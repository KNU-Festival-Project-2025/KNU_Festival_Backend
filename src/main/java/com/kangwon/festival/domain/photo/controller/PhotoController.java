package com.kangwon.festival.domain.photo.controller;

import com.kangwon.festival.domain.photo.dto.PhotoRequest;
import com.kangwon.festival.domain.photo.service.PhotoService;
import com.kangwon.festival.domain.security.dto.CustomUserDetails;
import com.kangwon.festival.global.annotation.CurrentUser;
import com.kangwon.festival.global.dto.ApiResponseData;
import com.kangwon.festival.global.dto.ApiResponseMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/photo")
public class PhotoController {
    private final PhotoService photoService;

    @PostMapping
    public ResponseEntity<ApiResponseMessage> postPhoto(@CurrentUser CustomUserDetails user, @Valid @RequestPart("request") PhotoRequest request, @RequestPart("file") MultipartFile file ) {
        return ResponseEntity.ok(photoService.createPhoto(user, request, file));
    }

    @GetMapping
    public ResponseEntity<ApiResponseData> getPhotos(@CurrentUser CustomUserDetails user) {
        return ResponseEntity.ok(photoService.getPhotos(user));
    }

    @GetMapping("/{photoId}")
    public ResponseEntity<ApiResponseData> getPhoto(@CurrentUser CustomUserDetails user, @PathVariable Long photoId) {
        return ResponseEntity.ok(photoService.getPhoto(user, photoId));
    }

    @DeleteMapping("/{photoId}")
    public ResponseEntity<ApiResponseMessage> deletePhoto(@CurrentUser CustomUserDetails user, @PathVariable Long photoId) {
        return ResponseEntity.ok(photoService.deletePhoto(user, photoId));
    }
}
