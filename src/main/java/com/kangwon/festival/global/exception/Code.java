package com.kangwon.festival.global.exception;

import java.util.Optional;
import java.util.function.Predicate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum Code {

    /**
     * 성공 0번대
     */
    SUCCESS(HttpStatus.OK, 0, "성공적으로 처리되었습니다."),

    /**
     * VALIDATION 관련 100번대
     */
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, 100, "잘못된 입력값이 존재합니다."),

    /**
     * 400번대
     */
    //유효하지 않은(잘못된) 입력값(40000 ~ 40099번대)
    INVALID_INPUT(HttpStatus.BAD_REQUEST, 40000, "잘못된 값이 존재합니다."),
    DUPLICATE_NICKNAME(HttpStatus.BAD_REQUEST, 40001, "이미 사용 중인 닉네임입니다."),
    SAME_NICKNAME(HttpStatus.BAD_REQUEST, 40002, "동일한 닉네임으로 변경할 수 없습니다."),
    USER_ALREADY_BANNED(HttpStatus.BAD_REQUEST, 40003, "이미 차단된 사용자입니다."),
    MISSING_REQUIRED_INPUT(HttpStatus.BAD_REQUEST, 40004, "필수 입력값이 누락되었습니다."),

    //유효하지 않은 리소스(40100 ~ 40199번대)
    CAN_NOT_FIND_RESOURCE(HttpStatus.BAD_REQUEST, 40100, "해당 리소스를 찾을 수 없습니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, 40101, "사용자를 찾을 수 없습니다."),
    CAN_NOT_FIND_USER(HttpStatus.BAD_REQUEST, 40101, "해당 유저를 찾을 수 없습니다."),
    CAN_NOT_FIND_BLOCKER_USER(HttpStatus.BAD_REQUEST, 40102, "차단한 사용자를 찾을 수 없습니다."),
    CAN_NOT_FIND_BLOCKED_USER(HttpStatus.BAD_REQUEST, 40103, "차단된 사용자를 찾을 수 없습니다."),
    NOT_FOUND_PHOTO(HttpStatus.NOT_FOUND, 40104, "사진 게시글을 찾을 수 없습니다."),
    INVALID_FILE_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, 40105, "허용되지 않은 파일 형식입니다."),
    FORBIDDEN_PHOTO_DELETE(HttpStatus.FORBIDDEN, 40106, "본인의 게시글만 삭제할 수 있습니다."),
    PHOTO_UPLOAD_LIMIT(HttpStatus.BAD_REQUEST, 40107, "사진 게시글은 최대 5개까지만 올릴 수 있습니다."),

    //보안 관련(40200 ~ 40299번대)
    REQUIRED_LOGIN(HttpStatus.UNAUTHORIZED, 40200, "로그인이 필요합니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, 40201, "유효하지 않은 토큰입니다."),
    KAKAO_INVALID_TOKEN(HttpStatus.UNAUTHORIZED, 40211, "카카오 토큰 교환에 실패하였습니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, 40202, "토큰이 만료되었습니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, 40203, "접근 권한이 없습니다."),

    /**
     * 500번대
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "예기치 못한 서버 오류가 발생했습니다."),
    FILE_UPLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 501, "파일 업로드를 실패하였습니다."),
    DELETE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 502, "데이터베이스 삭제를 실패하였습니다."),
    STORAGE_DELETE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 503, "GCR 이미지 삭제를 실패하였습니다."),
    ;

    private final HttpStatus status;
    private final Integer code;
    private final String message;

    public String getMessage(Throwable e) {
        return this.getMessage(this.getMessage() + " - " + e.getMessage());
        // 결과 예시 - "Validation error - Reason why it isn't valid"
    }

    public String getMessage(String message) {
        return Optional.ofNullable(message)
                .filter(Predicate.not(String::isBlank))
                .orElse(this.getMessage());
    }

    public String getDetailMessage(String message) {
        return this.getMessage() + " : " + message;
    }
}