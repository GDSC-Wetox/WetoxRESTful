package dev.wetox.WetoxRESTful.auth;

import dev.wetox.WetoxRESTful.exception.MemberNotFoundException;
import dev.wetox.WetoxRESTful.jwt.JwtService;
import dev.wetox.WetoxRESTful.user.OAuthProvider;
import dev.wetox.WetoxRESTful.user.User;
import dev.wetox.WetoxRESTful.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static dev.wetox.WetoxRESTful.user.Role.USER;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private static final String password = "wetokWkdWkd";

    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
//        todo nickname 유일설 검사, 인증 로직 추가
//        todo OpenId -> 카카오 아이디 kakaoId
        String nickname = request.getNickname();
        String oauthId = null; // todo kakaoId 가져오기
        OAuthProvider oauthProvider = request.getOauthProvider();

        User user = User.builder()
                .nickname(nickname)
                .role(USER)
                .password(passwordEncoder.encode(password))
                .oauthId(oauthId)
                .oauthProvider(oauthProvider)
                .build();
        userRepository.save(user);
        String jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwt)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
//        todo OAuth 서버와 추가 인증 로직 구현할 것
//        todo OpenID -> 카카오 아이디 kakaoId
//        todo kakaoId -> User 엔티티 : select u from User u where oauthId = kakaoId and oauthProvider = kakao
//        todo User 엔티티 -> nickname
        String nickname = null; // todo nickname 가져오기

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        nickname,
                        password
                )
        );
        User user = userRepository.findByNickname(nickname)
                .orElseThrow(MemberNotFoundException::new);
        String jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwt)
                .build();
    }
}
