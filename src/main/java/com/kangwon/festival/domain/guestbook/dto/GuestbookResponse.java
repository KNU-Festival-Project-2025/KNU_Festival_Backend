package com.kangwon.festival.domain.guestbook.dto;

import com.kangwon.festival.global.entity.GuestbookInfo;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GuestbookResponse {
    private Long guestbookId;
    private String userNickname;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static GuestbookResponse from(GuestbookInfo g) {
        return new GuestbookResponse(
                g.getGuestbookId(),
                g.getUser().getUserNickname(),
                g.getGuestbookContent(),
                g.getCreatedDateTime(),
                g.getModifiedDateTime()
        );
    }
}
