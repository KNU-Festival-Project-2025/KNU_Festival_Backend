package com.kangwon.festival.main.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserBlockedResponse {
    private int userBlockId;
    private int blockerId;
    private String blockerNickname;
    private int blockedUserId;
    private String blockedUserNickname;

    private boolean blockedUserBanned;

    private LocalDateTime createdDateTime;

}
