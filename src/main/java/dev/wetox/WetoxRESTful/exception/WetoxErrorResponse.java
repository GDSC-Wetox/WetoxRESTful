package dev.wetox.WetoxRESTful.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WetoxErrorResponse {
    private LocalDateTime timestamp;
    private String error;
}
