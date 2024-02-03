package dev.wetox.WetoxRESTful.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/search")
    public ResponseEntity<List<UserResponse>> ser(
            @RequestBody UserNicknameRequest request
    ) {
        return ResponseEntity.ok(userService.searchFriendsByNickname(request.getNickname()));
    }
}
