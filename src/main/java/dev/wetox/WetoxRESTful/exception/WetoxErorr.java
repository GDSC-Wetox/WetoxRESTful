package dev.wetox.WetoxRESTful.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum WetoxErorr {
    DUPLICATED_USER_REGISTER("중복된 사용자 등록입니다.", BAD_REQUEST),
    MEMBER_NOT_FOUND("존재하지 않는 사용자입니다.", NOT_FOUND),
    SCREEN_TIME_NOT_FOUND("스크린 타임이 존재하지 않습니다.", NOT_FOUND),

    OIDC_VALIDATION_FAIL("OIDC 인증에 실패했습니다.", UNAUTHORIZED),
    OIDC_INVALID_HEADER("OIDC 헤더를 인식할 수 없습니다.", BAD_REQUEST),
    OIDC_INVALID_PUBLIC_ID("OIDC 공개키 아이디를 인식할 수 없습니다.", BAD_REQUEST),
    ;

    private final String message;
    private final HttpStatus httpStatus;
}
