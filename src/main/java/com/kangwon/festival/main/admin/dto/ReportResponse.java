package com.kangwon.festival.main.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReportResponse {
    private Long reportId;
    private Long reportedUserId;
    private Long reporterUserId;
    private String reason;
    private LocalDateTime createdDateTime;
}
