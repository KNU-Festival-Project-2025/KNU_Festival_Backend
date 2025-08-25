package com.kangwon.festival.global.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "guestbook_info")
public class GuestbookInfo extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer guestbookId;

    @ManyToOne
    @JoinColumn(name = "guestbook_user_id")
    private UserInfo user;

    @Column(nullable = false)
    private boolean guestbookIsAnonymous = true;

    @Column(nullable = false)
    private String guestbookTitle;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String guestbookContent;

}
