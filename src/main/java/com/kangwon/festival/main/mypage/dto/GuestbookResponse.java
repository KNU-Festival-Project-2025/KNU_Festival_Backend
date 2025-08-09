package com.kangwon.festival.main.mypage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class GuestbookResponse {
    private Integer guestbookId;
    private String writerNickname;
    private Integer userId;
    private boolean guestbookIsAnonymous;
    private String guestbookTitle;
    private String guestbookContent;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
