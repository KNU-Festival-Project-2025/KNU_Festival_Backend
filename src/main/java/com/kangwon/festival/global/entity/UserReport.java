package com.kangwon.festival.global.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "user_report")
public class UserReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userReportId;

    @ManyToOne
    @JoinColumn(name = "reporter_id", nullable = false)
    private UserInfo reporter;

    @ManyToOne
    @JoinColumn(name = "reported_user_id", nullable = false)
    private UserInfo reportedUser;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String reportReason;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

}
