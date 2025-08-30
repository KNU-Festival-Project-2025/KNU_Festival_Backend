package com.kangwon.festival.global.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_info")
public class UserInfo extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(nullable = false, unique = true, length = 16)
    private String userNickname;

    @Column(nullable = false)
    private String userPassword;

    @Column(nullable = false)
    private LocalDate userBirth;

    @Column(name = "user_is_deleted", nullable = false)
    private boolean deleted = false;

    @Column(name = "user_is_banned", nullable = false)
    private boolean banned = false;

    public void ban() {
        this.banned = true;
    }

    public void unban() {
        this.banned = false;
    }

    public void changeNickname(String newNickname) {
        if (newNickname == null || newNickname.isBlank())
            return;
        this.userNickname = newNickname;
    }

    public void delete() {
        this.deleted = true;
    }
    public void restore() {
        this.deleted = false;
    }


}
