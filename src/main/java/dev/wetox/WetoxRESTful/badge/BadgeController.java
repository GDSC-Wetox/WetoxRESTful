package dev.wetox.WetoxRESTful.badge;

import dev.wetox.WetoxRESTful.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/badge")
@RequiredArgsConstructor
public class BadgeController {
    private final BadgeService badgeService;

    @GetMapping
    public ResponseEntity<List<UserBadgeResponse>> listRewardedBadge(
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(badgeService.listRewardedBadge(user.getId()));
    }

    @PostMapping
    public ResponseEntity<List<UserBadgeResponse>> updateBadge(
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(badgeService.updateBadge(user.getId()));
    }
}
