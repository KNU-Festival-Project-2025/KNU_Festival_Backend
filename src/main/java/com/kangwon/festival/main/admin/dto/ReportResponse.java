package com.kangwon.festival.main.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReportResponse {
    private Integer reportId;
    private Integer reportedUserId;
    private Integer reporterUserId;
    private String reason;
    private LocalDateTime createdAt;
}
