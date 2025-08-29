package com.kangwon.festival.main.guestbook.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuestbookRequest {
    private String title;
    private String content;
    private boolean anonymous;
}