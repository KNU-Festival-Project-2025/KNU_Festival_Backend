package com.kangwon.festival.domain.guestbook.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GuestbookRequest {
    private Long userId;
    private String Nickname;
    private String content;
}