package dev.wetox.WetoxRESTful.auth;

import dev.wetox.WetoxRESTful.user.OAuthProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String nickname;
    private OAuthProvider oauthProvider;
    private String openId;
}
