package dev.wetox.WetoxRESTful.friendship;

import dev.wetox.WetoxRESTful.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/friendship")
public class FriendshipController {

    private final FriendshipService friendshipService;

    @PostMapping("/request/{toId}")
    public ResponseEntity<FriendshipCreateResponse> create(@AuthenticationPrincipal User user, @PathVariable Long toId) {
        FriendshipCreateResponse friendship = friendshipService.create(user.getId(), toId);
        return ResponseEntity.ok(friendship);
    }

    @PutMapping("/request/{toId}")
    public ResponseEntity<String> acceptFriendship(@AuthenticationPrincipal User user, @PathVariable Long toId) {
        friendshipService.accept(user.getId(), toId);
        return ResponseEntity.ok("친구관계 승인 완료");
    }

   /* @GetMapping("/{id}")
    public ResponseEntity<List<FriendshipResponse>> findFriendshipById(@PathVariable Long id) {
        return ResponseEntity.ok(friendshipService.findMutualAcceptedFriendships(id));
    }*/
}
