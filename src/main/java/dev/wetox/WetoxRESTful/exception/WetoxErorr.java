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
    SCREEN_TIME_NOT_FOUND("스크린 타임이 존재하지 않습니다.", NOT_FOUND),
    FRIENDSHIP_EXIST("이미 친구관계가 존재합니다.", BAD_REQUEST),
    FRIENDSHIP_REQUEST_NOT_FOUND("친구 요청이 존재하지 않습니다.", NOT_FOUND),
    NOT_REQUEST_MYSELF("자기 자신에게 친구 요청을 보낼 수 없습니다.", BAD_REQUEST)
    ;

    private final String message;
    private final HttpStatus httpStatus;
}
