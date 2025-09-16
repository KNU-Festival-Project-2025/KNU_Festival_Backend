package com.kangwon.festival.domain.admin.dto;

import com.kangwon.festival.global.entity.UserBlock;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserBlockedResponse {
    private Long userBlockId;
    private Long blockerId;
    private String blockerNickname;
    private Long blockedUserId;
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
