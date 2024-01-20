package dev.wetox.WetoxRESTful.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@RequiredArgsConstructor
public enum WetoxErorr {
    DUPLICATED_USER_REGISTER("중복된 사용자 등록입니다.", BAD_REQUEST),
    MEMBER_NOT_FOUND("존재하지 않는 사용자입니다.", NOT_FOUND),
    ;

    private final String message;
    private final HttpStatus httpStatus;
}
