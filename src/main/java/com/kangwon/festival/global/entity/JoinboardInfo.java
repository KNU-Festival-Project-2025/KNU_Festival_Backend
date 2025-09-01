package com.kangwon.festival.global.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "joinboard_info")
public class JoinboardInfo extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer joinboardId;

    @ManyToOne
    @JoinColumn(name = "joinboard_user_id", nullable = false)
    private UserInfo user;

    @Column(nullable = false)
    private String joinboardTitle;

    @Column(nullable = false)
    private boolean joinboardTable;

    private Integer joinboardHeadcount;

}
