package dev.wetox.WetoxRESTful.screentime;

import dev.wetox.WetoxRESTful.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/screentime")
@RequiredArgsConstructor
public class ScreenTimeController {
    private final ScreenTimeService screenTimeService;

    @PostMapping
    public ResponseEntity<List<AppScreenTimeResponse>> updateScreenTime(
            @AuthenticationPrincipal User user,
            @RequestBody List<AppScreenTimeRequst> request) {
        return ResponseEntity.ok(screenTimeService.updateScreenTime(user.getId(), request));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<AppScreenTimeResponse>> findScreenTime(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(screenTimeService.findScreenTime(userId));
    }
}
