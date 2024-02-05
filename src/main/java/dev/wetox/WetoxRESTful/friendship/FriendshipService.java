package dev.wetox.WetoxRESTful.friendship;

import dev.wetox.WetoxRESTful.exception.FriendshipExistException;
import dev.wetox.WetoxRESTful.exception.FriendshipRequestNotFoundException;
import dev.wetox.WetoxRESTful.exception.NotRequestMyselfException;
import dev.wetox.WetoxRESTful.user.User;
import dev.wetox.WetoxRESTful.user.UserRepository;
import dev.wetox.WetoxRESTful.user.UserResponse;
import dev.wetox.WetoxRESTful.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class FriendshipService {


    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    //친구 관계 요청
    @Transactional
    public FriendshipCreateResponse create(Long fromUserId, Long toUserId) {
        if(Objects.equals(fromUserId, toUserId)) {
            throw new NotRequestMyselfException();
        }

        User fromUser = userRepository.findById(fromUserId).orElseThrow(EntityNotFoundException::new);
        User toUser = userRepository.findById(toUserId).orElseThrow(EntityNotFoundException::new);

        friendshipRepository.findByToIdAndFromId(toUserId, fromUserId).ifPresent(friendship -> {
            throw new FriendshipExistException();
        });

        friendshipRepository.findByToIdAndFromId(fromUserId, toUserId).ifPresent(friendship -> {
            throw new FriendshipExistException();
        });


        Friendship friendshipFrom = Friendship.builder()
                .from(fromUser)
                .to(toUser)
                .status(FriendshipStatus.REQUEST)
                .build();

        friendshipRepository.save(friendshipFrom);

        // todo: toUser에게 친구 신청이 도착했다는 푸시 알림

        return FriendshipCreateResponse.from(friendshipFrom);
    }

    //친구관계 수락
    @Transactional
    public FriendshipAcceptResponse accept(Long toId, Long fromId) {
        if(Objects.equals(toId, fromId)) {
            throw new NotRequestMyselfException();
        }
        Friendship friendship = friendshipRepository.findByToIdAndFromId(toId, fromId)
                .orElseThrow(() -> {
                    return new FriendshipRequestNotFoundException();
                });
        if(friendship.getStatus() == FriendshipStatus.ACCEPT) {
            throw new FriendshipExistException();
        }
        friendship.accept();

        Friendship newFriendShip = Friendship.builder()
                .from(friendship.getTo())
                .to(friendship.getFrom())
                .status(FriendshipStatus.ACCEPT)
                .build();
        friendshipRepository.save(newFriendShip);

        return FriendshipAcceptResponse.from(friendship);
    }

    //전체 친구 목록 조회
    @Transactional(readOnly = true)
    public List<UserResponse> getFriendShip(Long userId) {
        List<Friendship> friendShips = friendshipRepository.findByToIdAndStatus(userId, FriendshipStatus.ACCEPT);
        return friendShips.stream()
                .map(friendship -> friendship.getFrom().getId())
                .map(userService::retrieveProfile)
                .toList();
    }

    //나에게 친구요청을 보낸 친구목록
    public List<FriendshipResponse> findByToIdAndStatus(Long toId, FriendshipStatus status) {
        return FriendshipResponse.from(friendshipRepository.findByToIdAndStatus(toId, status));
    }
}
