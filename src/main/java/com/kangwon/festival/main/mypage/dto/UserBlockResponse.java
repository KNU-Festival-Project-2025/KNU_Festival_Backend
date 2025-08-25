package com.kangwon.festival.main.mypage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class UserBlockResponse {
    private Integer userBlockId;

    private Integer blockerId;
    private String blockerNickname;

    private Integer blockedId;
    private String blockedNickname;

    private LocalDateTime createdDateTime;
}