package com.kangwon.festival.main.admin.dto;


import com.kangwon.festival.global.entity.UserReport;
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

    public static UserReportResponse from(UserReport r) {
        return new UserReportResponse(
                r.getUserReportId(),
                r.getReporter().getUserId(),
                r.getReporter().getUserNickname(),
                r.getReportReason(),
                r.getReportedUser().getUserId(),
                r.getReportedUser().getUserNickname(),
                r.getReportedUser().isBanned(),
                r.getCreatedDateTime()
        );
    }



}
