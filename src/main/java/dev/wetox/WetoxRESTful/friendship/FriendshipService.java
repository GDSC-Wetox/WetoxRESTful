package dev.wetox.WetoxRESTful.friendship;

import dev.wetox.WetoxRESTful.screentime.ScreenTime;
import dev.wetox.WetoxRESTful.screentime.ScreenTimeRepository;
import dev.wetox.WetoxRESTful.user.User;
import dev.wetox.WetoxRESTful.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class FriendshipService {


    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;
    private final ScreenTimeRepository screenTimeRepository;

    //친구 관계 요청
    @Transactional
    public FriendshipCreateResponse create(Long fromUserId, Long toUserId) {
        if(Objects.equals(fromUserId, toUserId)) {
            log.error("자기 자신에게 친구 요청을 보낼 수 없습니다.");
            throw new IllegalStateException("자기 자신에게 친구 요청을 보낼 수 없습니다.");
        }

        User fromUser = userRepository.findById(fromUserId).orElseThrow(EntityNotFoundException::new);
        User toUser = userRepository.findById(toUserId).orElseThrow(EntityNotFoundException::new);

        friendshipRepository.findByToIdAndFromId(toUserId, fromUserId).ifPresent(friendship -> {
            log.error("이미 친구관계가 존재합니다.");
            throw new IllegalStateException("이미 친구관계가 존재합니다.");
        });

        friendshipRepository.findByToIdAndFromId(fromUserId, toUserId).ifPresent(friendship -> {
            log.error("이미 친구관계가 존재합니다.");
            throw new IllegalStateException("이미 친구관계가 존재합니다.");
        });


        Friendship friendshipFrom = Friendship.builder()
                .from(fromUser)
                .to(toUser)
                .status(FriendshipStatus.REQUEST)
                .build();

        friendshipRepository.save(friendshipFrom);

        return FriendshipCreateResponse.from(friendshipFrom);
    }

    //친구관계 수락
    @Transactional
    public FriendshipAcceptResponse accept(Long toId, Long fromId) {
        if(Objects.equals(toId, fromId)) {
            log.error("자기 자신에게 친구 요청을 보낼 수 없습니다.");
            throw new IllegalStateException("자기 자신에게 친구 요청을 보낼 수 없습니다.");
        }
        Friendship friendship = friendshipRepository.findByToIdAndFromId(fromId, toId)
                .orElseThrow(() -> {
                    log.error("친구 요청이 존재하지 않습니다.");
                    return new IllegalStateException("친구 요청이 존재하지 않습니다.");
                });
        if(friendship.getStatus() == FriendshipStatus.ACCEPT) {
            log.error("이미 친구관계가 존재합니다.");
            throw new IllegalStateException("이미 친구관계가 존재합니다.");
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

    @Transactional(readOnly = true)
    public List<FriendshipListResponse> getFriendShip(Long userId) {
        List<Friendship> friendShips = friendshipRepository.findByToIdAndStatus(userId, FriendshipStatus.ACCEPT);
        List<FriendshipListResponse> responses = new ArrayList<>();
        for(Friendship friendship : friendShips) {
            List<ScreenTime> screenTimes = screenTimeRepository.findScreenTimeByUserId(friendship.getFrom().getId());
            double totalDuration = screenTimes.stream()
                    .mapToDouble(ScreenTime::getTotalDuration)
                    .sum();

            responses.add(FriendshipListResponse.builder()
                    .userId(friendship.getFrom().getId())
                    .nickname(friendship.getFrom().getNickname())
                    .totalDuration(totalDuration)
                    .build());
        }
        return responses;
    }
}
