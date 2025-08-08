package com.kangwon.festival.main.mypage.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "user_info")
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(unique = true, nullable = false, length = 16)
    private String userNickname;

    @Column(nullable = false)
    private String userPassword;

    @Column(nullable = false)
    private LocalDate userBirth;

    @Column(name = "user_is_deleted", nullable = false)
    private boolean isDeleted = false;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();


}
