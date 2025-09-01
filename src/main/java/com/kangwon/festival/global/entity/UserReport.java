package com.kangwon.festival.global.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_report")
public class UserReport extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userReportId;

    @ManyToOne
    @JoinColumn(name = "reporter_id", nullable = false)
    private UserInfo reporter;

    @ManyToOne
    @JoinColumn(name = "reported_user_id", nullable = false)
    private UserInfo reportedUser;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String reportReason;

}
