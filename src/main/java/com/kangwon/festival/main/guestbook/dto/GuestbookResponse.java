package com.kangwon.festival.main.guestbook.dto;

import com.kangwon.festival.global.entity.GuestbookInfo;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GuestbookResponse {
    private Long guestbookId;
    private Long userId;
    private String userNickname;
    private String title;
    private String content;
    private boolean deleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static GuestbookResponse from(GuestbookInfo g) {
        return new GuestbookResponse(
                g.getGuestbookId(),
                g.getUser().getUserId(),
                g.getUser().getUserNickname(),
                g.getGuestbookTitle(),
                g.getGuestbookContent(),
                g.isGuestbookIsDeleted(),
                g.getCreatedDateTime(),
                g.getModifiedDateTime()
        );
    }
}
