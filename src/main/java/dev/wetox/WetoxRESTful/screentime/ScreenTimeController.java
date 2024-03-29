package dev.wetox.WetoxRESTful.screentime;

import dev.wetox.WetoxRESTful.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/screentime")
@RequiredArgsConstructor
public class ScreenTimeController {
    private final ScreenTimeService screenTimeService;

    @PostMapping
    public ResponseEntity<ScreenTimeResponse> updateScreenTime(
            @AuthenticationPrincipal User user,
            @RequestBody List<CategoryScreenTimeRequest> request) {
        return ResponseEntity.ok(screenTimeService.updateScreenTime(user.getId(), request));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ScreenTimeResponse> retrieveScreenTime(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(screenTimeService.retrieveScreenTime(userId));
    }

    @GetMapping("")
    public ResponseEntity<ScreenTimeResponse> retrieveAuthenticatedUserScreenTime(
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(screenTimeService.retrieveScreenTime(user.getId()));
    }
}
