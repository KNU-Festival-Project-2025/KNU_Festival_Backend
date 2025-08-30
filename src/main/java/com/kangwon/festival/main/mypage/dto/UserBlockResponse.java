package com.kangwon.festival.main.mypage.dto;

import com.kangwon.festival.global.entity.UserBlock;
import com.kangwon.festival.main.admin.dto.UserBlockedResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class UserBlockResponse {
    private Long userBlockId;

    private Long blockerId;
    private String blockerNickname;

    private Long blockedId;
    private String blockedNickname;

    private LocalDateTime createdDateTime;

    public static UserBlockResponse from(UserBlock b) {
        return new UserBlockResponse(
                b.getUserBlockId(),
                b.getBlocker().getUserId(),
                b.getBlocker().getUserNickname(),
                b.getBlocked().getUserId(),
                b.getBlocked().getUserNickname(),
                b.getCreatedDateTime()
        );
    }
}