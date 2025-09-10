package com.kangwon.festival.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record SignInRequest(
        @NotBlank(message = "인가 코드는 필수 값입니다.")
        String code,

        @NotBlank(message = "닉네임은 필수 입력값입니다.", groups = ValidationGroups.SignUp.class)
        @Size(max = 50, message = "닉네임은 50자 이하여야 합니다.", groups = ValidationGroups.SignUp.class)
        String nickname,

        @NotBlank(message = "전화번호는 필수 입력값입니다.", groups = ValidationGroups.SignUp.class)
        @Pattern(regexp = "^01[0-9]{8,9}$", message = "전화번호 형식이 올바르지 않습니다. 예) 01012345678", groups = ValidationGroups.SignUp.class)
        String phone    
) {
}
