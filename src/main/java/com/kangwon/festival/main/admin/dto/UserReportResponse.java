package com.kangwon.festival.main.admin.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserReportResponse {
    private int userReportId;

    private int reporterId;
    private String reporterNickname;

    private String reportReason;

    private int reportedUserId;
    private String reportedUserNickname;
    private boolean reportedUserBanned;

    private LocalDateTime createdDateTime;
}
