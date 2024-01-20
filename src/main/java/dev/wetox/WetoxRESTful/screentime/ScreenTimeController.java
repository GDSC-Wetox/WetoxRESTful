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
            @RequestBody List<AppScreenTimeRequest> request) {
        return ResponseEntity.ok(screenTimeService.updateScreenTime(user.getId(), request));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ScreenTimeResponse> findScreenTime(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(screenTimeService.retrieveScreenTime(userId));
    }
}
