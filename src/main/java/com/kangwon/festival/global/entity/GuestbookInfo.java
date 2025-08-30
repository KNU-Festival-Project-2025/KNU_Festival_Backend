package com.kangwon.festival.global.entity;

import com.kangwon.festival.global.exception.ServiceException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.kangwon.festival.global.exception.Code.INVALID_INPUT;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Column(nullable = false)
    private boolean guestbookIsDeleted = false;

}
