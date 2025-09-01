package com.kangwon.festival.main.guestbook.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GuestbookRequest {
    private Long userId;
    private String title;
    private String content;
    private boolean anonymous;
}