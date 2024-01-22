package dev.wetox.WetoxRESTful.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JwtError {
    INVALID_JWT("Invalid JWT Token", 401),
    EXPIRED_JWT("Expired JWT Token", 401),
    UNSUPPORTED_JWT("Unsupported JWT Token", 401),
    ILLEGAL_JWT("JWT claims string is empty", 401),
    UNEXPECTED_JWT("Unexpected JWT error is occured", 401),
    ;

    private final String message;
    private final int httpStatus;
}
