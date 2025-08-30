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
    private Long guestbookId;

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

    private GuestbookInfo(UserInfo user, boolean guestbookIsAnonymous, String guestbookTitle, String guestbookContent) {
        this.user = user;
        this.guestbookIsAnonymous = guestbookIsAnonymous;
        this.guestbookTitle = guestbookTitle;
        this.guestbookContent = guestbookContent;
    }

    public static GuestbookInfo create(UserInfo user, boolean guestbookIsAnonymous, String title, String content) {
        if (title == null || title.isBlank()) {
            throw new ServiceException(INVALID_INPUT, "제목을 입력하세요.");
        }
        if (content == null || content.isBlank()) {
            throw new ServiceException(INVALID_INPUT, "내용을 입력하세요.");
        }

        return new GuestbookInfo(user, guestbookIsAnonymous, title, content);
    }

    public void changeTitle(String title) {
        if (title == null || title.isBlank())
            return;
        this.guestbookTitle = title;
    }

    public void changeContent(String content) {
        if (content == null || content.isBlank())
            return;
        this.guestbookContent = content;
    }

    public void changeAnonymous(boolean anonymous) {
        this.guestbookIsAnonymous = anonymous;
    }

    public void deleted() {
        this.guestbookIsDeleted = true;
    }

}
