package dev.wetox.WetoxRESTful.auth;

import dev.wetox.WetoxRESTful.jwt.JwtService;
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
        User user = User.builder()
                .nickname(request.getNickname())
                .role(USER)
                .password(passwordEncoder.encode(password))
                .build();
//        todo nickname 유일설 검사, 인증 로직 추가
        userRepository.save(user);
        String jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwt)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getNickname(),
                        password
                )
        );
//        todo OAuth 서버와 추가 인증 로직 구현할 것
        User user = userRepository.findByNickname(request.getNickname())
                .orElseThrow();
        String jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwt)
                .build();
    }
}
