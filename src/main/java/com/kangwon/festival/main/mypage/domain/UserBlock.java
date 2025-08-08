package com.kangwon.festival.main.mypage.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "user_block")
public class UserBlock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userBlockId;

    @ManyToOne
    @JoinColumn(name = "blocker_id", nullable = false)
    private UserInfo blocker;

    @ManyToOne
    @JoinColumn(name = "blocked_user_id", nullable = false)
    private UserInfo blocked;

    private LocalDateTime createdAt = LocalDateTime.now();

}
