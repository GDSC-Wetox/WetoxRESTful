package dev.wetox.WetoxRESTful.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class WetoxExceptionController {
    @ExceptionHandler(WetoxException.class)
    public ResponseEntity<WetoxErrorResponse> error(WetoxException e) {
        return new ResponseEntity<>(
                WetoxErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .error(e.getError().getMessage())
                        .build(),
                e.getError().getHttpStatus()
        );
    }
}
