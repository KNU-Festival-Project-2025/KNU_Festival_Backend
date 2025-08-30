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
@Table(name = "joinboard_info")
public class JoinboardInfo extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long joinboardId;

    @ManyToOne
    @JoinColumn(name = "joinboard_user_id", nullable = false)
    private UserInfo user;

    @Column(nullable = false)
    private String joinboardTitle;

    @Column(nullable = false)
    private boolean joinboardTable;

    private Integer joinboardHeadcount;

}
