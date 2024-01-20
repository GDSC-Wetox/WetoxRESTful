package dev.wetox.WetoxRESTful.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class WetoxException extends RuntimeException {
    private final WetoxErorr error;
}
