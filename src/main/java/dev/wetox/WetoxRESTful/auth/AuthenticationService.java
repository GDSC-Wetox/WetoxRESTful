package dev.wetox.WetoxRESTful.auth;

import dev.wetox.WetoxRESTful.exception.UserNotFoundException;
import dev.wetox.WetoxRESTful.exception.UserRegisterDuplicateException;
import dev.wetox.WetoxRESTful.image.ImageService;
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
import org.springframework.web.multipart.MultipartFile;

import static dev.wetox.WetoxRESTful.user.Role.USER;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final KakaoOIDCService kakaoOIDCService;
    private final ImageService imageService;

    private static final String password = "wetokWkdWkd";
    
    @Transactional
    public AuthenticationResponse register(
            String nickname,
            OAuthProvider oauthProvider,
            String openId,
            String deviceToken,
            MultipartFile profileImage
    ) {
        String subject = kakaoOIDCService.extractSubject(openId);

        if (userRepository
                .findByOauthProviderAndOauthSubject(oauthProvider, subject)
                .isPresent()) {
            throw new UserRegisterDuplicateException();
        }

        String imageUUID = imageService.uploadImage(profileImage);

        User user = User.builder()
                .nickname(nickname)
                .role(USER)
                .password(passwordEncoder.encode(password))
                .oauthSubject(subject)
                .oauthProvider(oauthProvider)
                .profileImageUUID(imageUUID)
                .deviceToken(deviceToken)
                .build();
        userRepository.save(user);
        String jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwt)
                .build();
    }

    public AuthenticationResponse authenticate(OAuthProvider oAuthProvider, String openId) {
        String subject = kakaoOIDCService.extractSubject(openId);
        User user = userRepository.findByOauthProviderAndOauthSubject(oAuthProvider, subject)
                .orElseThrow(UserNotFoundException::new);

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getNickname(),
                        password
                )
        );
        String jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwt)
                .build();
    }

}
