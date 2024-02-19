package dev.wetox.WetoxRESTful.friendship;

import com.google.firebase.messaging.FirebaseMessagingException;
import dev.wetox.WetoxRESTful.user.User;
import dev.wetox.WetoxRESTful.user.UserResponse;
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
    public ResponseEntity<FriendshipCreateResponse> create(@AuthenticationPrincipal User user, @PathVariable Long toId) throws FirebaseMessagingException {
        FriendshipCreateResponse friendship = friendshipService.create(user.getId(), toId);
        return ResponseEntity.ok(friendship);
    }

    @PostMapping("/accept/{toId}")
    public ResponseEntity<FriendshipAcceptResponse> accept(@AuthenticationPrincipal User user, @PathVariable Long toId) {
        FriendshipAcceptResponse friendship = friendshipService.accept(user.getId(), toId);
        return ResponseEntity.ok(friendship);
    }

    @GetMapping()
    public ResponseEntity<List<UserResponse>> getFriendship(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(friendshipService.getFriendShip(user.getId()));
    }

    @GetMapping("/request")
    public ResponseEntity<List<FriendshipResponse>> findFriendshipRequestById(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(friendshipService.findByToIdAndStatus(user.getId(), FriendshipStatus.REQUEST));
    }
}
