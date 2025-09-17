package com.kangwon.festival.domain.guestbook.dto;

import com.kangwon.festival.global.entity.GuestbookInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "GuestbookResponse", description = "방명록 단건 응답 DTO")
public class GuestbookResponse {
    @Schema(description = "방명록 ID")
    private Long guestbookId;
    @Schema(description = "작성자 ID")
    private String nickname;
    @Schema(description = "방명록 내용")
    private String content;
    @Schema(description = "생성 시각")
    private LocalDateTime createdAt;
    @Schema(description = "수정 시각")
    private LocalDateTime updatedAt;

    public static GuestbookResponse from(GuestbookInfo g) {
        return new GuestbookResponse(
                g.getGuestbookId(),
                g.getUser().getNickname(),
                g.getGuestbookContent(),
                g.getCreatedDateTime(),
                g.getModifiedDateTime()
        );
    }
}
