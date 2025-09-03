package com.kangwon.festival.main.mypage.dto;

import com.kangwon.festival.global.entity.GuestbookInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class GuestbookResponse {
    private Long guestbookId;
    private String writerNickname;
    private Long userId;
    private boolean guestbookIsAnonymous;
    private String guestbookTitle;
    private String guestbookContent;
    private LocalDateTime createdDateTime;
    private LocalDateTime modifiedDateTime;

    public static GuestbookResponse from(GuestbookInfo g) {
        return new GuestbookResponse(
                g.getGuestbookId(),
                g.getUser().getUserNickname(),
                g.getUser().getUserId(),
                g.isGuestbookIsAnonymous(),
                g.getGuestbookTitle(),
                g.getGuestbookContent(),
                g.getCreatedDateTime(),
                g.getModifiedDateTime()
        );
    }
}
