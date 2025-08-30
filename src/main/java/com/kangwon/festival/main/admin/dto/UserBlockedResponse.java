package com.kangwon.festival.main.admin.dto;

import com.kangwon.festival.global.entity.UserBlock;
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

    public static UserBlockedResponse from(UserBlock b) {
        return  new UserBlockedResponse(
                b.getUserBlockId(),
                b.getBlocker().getUserId(),
                b.getBlocker().getUserNickname(),
                b.getBlocked().getUserId(),
                b.getBlocked().getUserNickname(),
                b.getBlocked().isBanned(),
                b.getCreatedDateTime()
        );
    }
}
