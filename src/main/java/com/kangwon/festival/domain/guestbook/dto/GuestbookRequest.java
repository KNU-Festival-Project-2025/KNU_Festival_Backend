package com.kangwon.festival.domain.guestbook.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "GuestbookRequest", description = "방명록 등록 요청 DTO")
public class GuestbookRequest {
    @Schema(description = "사용자 ID", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer userId;
    @Schema(description = "사용자 닉네임", accessMode = Schema.AccessMode.READ_ONLY)
    private String Nickname;
    @Schema(description = "방명록 내용", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(max = 100, message = "방명록 내용은 100자 이하여야 합니다.")
    private String content;
}