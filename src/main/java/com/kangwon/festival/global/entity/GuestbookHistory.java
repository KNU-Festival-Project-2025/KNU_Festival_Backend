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
@Table(name = "guestbook_history")
public class GuestbookHistory extends BaseTime{

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long guestbookHistoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guestbook_id", nullable = false)
    private GuestbookInfo guestbook;

    @Column(nullable = false)
    private String guestbookHistoryTitle;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String guestbookHistoryContent;

}
