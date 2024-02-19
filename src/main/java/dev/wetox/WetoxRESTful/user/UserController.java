package dev.wetox.WetoxRESTful.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> retrieveProfile(
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(userService.retrieveProfile(user.getId()));
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<UserResponse> retrieveProfileByUserId(
        @PathVariable Long userId
    ) {
        return ResponseEntity.ok(userService.retrieveProfile(userId));
    }

    @PostMapping("/search")
    public ResponseEntity<UserResponse> searchByNickname(
            @RequestBody UserNicknameRequest request
    ) {
        return ResponseEntity.ok(userService.retrieveProfileByNickname(request.getNickname()));
    }
}
