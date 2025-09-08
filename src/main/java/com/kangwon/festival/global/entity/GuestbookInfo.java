package com.kangwon.festival.global.entity;

import com.kangwon.festival.global.exception.ServiceException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.kangwon.festival.global.exception.Code.MISSING_REQUIRED_INPUT;

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

    @Column(nullable = false, columnDefinition = "TEXT")
    private String guestbookContent;

    @Column(nullable = false)
    private boolean guestbookIsDeleted = false;

    private GuestbookInfo(UserInfo user, String guestbookContent) {
        this.user = user;
        this.guestbookContent = guestbookContent;
    }

    public static GuestbookInfo create(UserInfo user, String content) {
        if (content == null || content.isBlank()) {
            throw new ServiceException(MISSING_REQUIRED_INPUT, "내용을 입력하세요.");
        }

        return new GuestbookInfo(user, content);
    }

    /*
    public void changeContent(String content) {
        if (content == null || content.isBlank())
            return;
        this.guestbookContent = content;
    }*/

    public void deleted() {
        this.guestbookIsDeleted = true;
    }

}
