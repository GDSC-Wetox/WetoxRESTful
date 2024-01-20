package dev.wetox.WetoxRESTful.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtErrorResponse {
    private String timestamp;
    private String error;
}
