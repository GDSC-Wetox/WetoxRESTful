package dev.wetox.WetoxRESTful.auth;

import dev.wetox.WetoxRESTful.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserService userService;

    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestParam("nickname") String nickname,
            @RequestParam("oauthProvider") OAuthProvider oauthProvider,
            @RequestParam("openId") String openId,
            @RequestParam("profileImage") MultipartFile profileImage
            ) {
        return ResponseEntity.ok(authenticationService.register(
                nickname,
                oauthProvider,
                openId,
                profileImage
        ));
    }

    @PostMapping("/token")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestParam OAuthProvider oauthProvider,
            @RequestParam String openId
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(
                oauthProvider,
                openId
        ));
    }

    @GetMapping("/valid")
    public ResponseEntity<String> valid() {
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/valid/nickname")
    public ResponseEntity<UserDuplicatedConfirmResponse> checkNicknameDuplicated(
            @RequestBody UserNicknameRequest request
    ) {
        return ResponseEntity.ok(userService.checkNicknameDuplicated(request.getNickname()));
    }
}
