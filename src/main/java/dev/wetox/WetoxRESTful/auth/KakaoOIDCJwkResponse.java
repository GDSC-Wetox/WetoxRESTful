package dev.wetox.WetoxRESTful.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KakaoOIDCJwkResponse {
    private String kid;
    private String kty;
    private String alg;
    private String use;
    private String n;
    private String e;
}
