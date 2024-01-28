package dev.wetox.WetoxRESTful.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KakaoOIDCHeaderResponse {
    private String kid;
    private String typ;
    private String alg;
}
