package com.kangwon.festival.global.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "notice_info")
public class NoticeInfo extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer noticeId;

    @Column(nullable = false)
    private String noticeTitle;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String noticeContent;


}
