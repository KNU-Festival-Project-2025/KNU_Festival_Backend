package com.kangwon.festival.main.guestbook.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuestbookResponse {
    private Long guestbookId;
    private Long userId;
    private String title;
    private String content;
    private boolean anonymous;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
