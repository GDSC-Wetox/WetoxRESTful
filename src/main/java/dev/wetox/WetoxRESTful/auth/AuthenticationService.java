package dev.wetox.WetoxRESTful.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.wetox.WetoxRESTful.exception.MemberNotFoundException;
import dev.wetox.WetoxRESTful.jwt.JwtService;
import dev.wetox.WetoxRESTful.user.OAuthProvider;
import dev.wetox.WetoxRESTful.user.User;
import dev.wetox.WetoxRESTful.user.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

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
    public AuthenticationResponse register(String nickname, OAuthProvider oauthProvider, String openId, MultipartFile profileImage) {
//        todo nickname 유일설 검사, 인증 로직 추가
//        todo OpenId -> 카카오 아이디 kakaoId

        // get public key (Base64urlUInt encoded value)
        // todo: openId의 kid와 일치하는 공개키 가져오는 로직 구현
        String n = "qGWf6RVzV2pM8YqJ6by5exoixIlTvdXDfYj2v7E6xkoYmesAjp_1IYL7rzhpUYqIkWX0P4wOwAsg-Ud8PcMHggfwUNPOcqgSk1hAIHr63zSlG8xatQb17q9LrWny2HWkUVEU30PxxHsLcuzmfhbRx8kOrNfJEirIuqSyWF_OBHeEgBgYjydd_c8vPo7IiH-pijZn4ZouPsEg7wtdIX3-0ZcXXDbFkaDaqClfqmVCLNBhg3DKYDQOoyWXrpFKUXUFuk2FTCqWaQJ0GniO4p_ppkYIf4zhlwUYfXZEhm8cBo6H2EgukntDbTgnoha8kNunTPekxWTDhE5wGAt6YpT4Yw";
        String e = "AQAB"; // 65537

        // Decode Base64urlUInt value
        // todo: Base64urlUInt decode 메소드로 추출
        String paddedN = n.length() % 4 == 0 ? n : n + "====".substring(n.length() % 4);
        String base64N = paddedN.replace('_', '/').replace('-', '+');

        byte[] nBytes = Base64.getDecoder().decode(base64N.getBytes());
        byte[] eBytes = Base64.getDecoder().decode(e.getBytes());

        BigInteger modulus = new BigInteger(1, nBytes);
        BigInteger exponent = new BigInteger(1, eBytes);

        // Get public key
        // todo: public key 생성 메소드로 추출
        PublicKey publicKey = null;
        try {
            RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(modulus, exponent);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(rsaPublicKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            throw new RuntimeException(ex);
        }

        // Extract subject
        // todo: 메소드 추출, JwtService에 추가
        String subject = Jwts.parser()
                .verifyWith(publicKey)
                .build()
                .parseSignedClaims(openId)
                .getPayload()
                .getSubject();

        // Regist user with subject
        User user = User.builder()
                .nickname(nickname)
                .role(USER)
                .password(passwordEncoder.encode(password))
                .oauthSubject(subject)
                .oauthProvider(oauthProvider)
                .build();
        userRepository.save(user);
        String jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwt)
                .build();
    }

    public AuthenticationResponse authenticate(OAuthProvider oAuthProvider, String openId) {
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
