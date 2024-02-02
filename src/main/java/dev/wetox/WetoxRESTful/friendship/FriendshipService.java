package dev.wetox.WetoxRESTful.friendship;

import dev.wetox.WetoxRESTful.exception.FriendshipExistException;
import dev.wetox.WetoxRESTful.exception.FriendshipRequestNotFoundException;
import dev.wetox.WetoxRESTful.exception.NotRequestMyselfException;
import dev.wetox.WetoxRESTful.image.ImageService;
import dev.wetox.WetoxRESTful.screentime.ScreenTime;
import dev.wetox.WetoxRESTful.screentime.ScreenTimeRepository;
import dev.wetox.WetoxRESTful.user.User;
import dev.wetox.WetoxRESTful.user.UserRepository;
import dev.wetox.WetoxRESTful.user.UserResponse;
import dev.wetox.WetoxRESTful.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class FriendshipService {


    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;
    private final ScreenTimeRepository screenTimeRepository;
    private final ImageService imageService;
    private final UserService userService;

    //친구 관계 요청
    @Transactional
    public FriendshipCreateResponse create(Long fromUserId, Long toUserId) {
        if (Objects.equals(fromUserId, toUserId)) {
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

        return FriendshipCreateResponse.from(friendshipFrom);
    }

    //친구관계 수락
    @Transactional
    public FriendshipAcceptResponse accept(Long toId, Long fromId) {
        if (Objects.equals(toId, fromId)) {
            throw new NotRequestMyselfException();
        }
        Friendship friendship = friendshipRepository.findByToIdAndFromId(toId, fromId)
                .orElseThrow(() -> {
                    return new FriendshipRequestNotFoundException();
                });
        if (friendship.getStatus() == FriendshipStatus.ACCEPT) {
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
    public List<FriendshipListResponse> getFriendShip(Long userId) {
        List<Friendship> friendShips = friendshipRepository.findByToIdAndStatus(userId, FriendshipStatus.ACCEPT);
        List<FriendshipListResponse> responses = new ArrayList<>();

        for (Friendship friendship : friendShips) {
            Long friendId = friendship.getFrom().getId();
            Page<ScreenTime> screenTimePage
                    = screenTimeRepository.findLatestByUserId(friendId, PageRequest.of(0, 1));
            List<ScreenTime> screenTimes = screenTimePage.getContent();
            Long totalDuration = 0L;
            if (!screenTimes.isEmpty()) {
                ScreenTime latestScreenTime = screenTimes.get(0);
                totalDuration = latestScreenTime.getTotalDuration();
            }

            responses.add(FriendshipListResponse.builder()
                    .userId(friendship.getFrom().getId())
                    .nickname(friendship.getFrom().getNickname())
                    .totalDuration(totalDuration)
                    .profileImage(imageService.getImageUrl(friendship.getFrom().getProfileImageUUID()))
                    .build());

        }
        return responses;
    }

    //나에게 친구요청을 보낸 친구목록
    public List<FriendshipResponse> findByToIdAndStatus(Long toId, FriendshipStatus status) {
        return FriendshipResponse.from(friendshipRepository.findByToIdAndStatus(toId, status));
    }

    //닉네임으로 친구 검색
    public List<UserResponse> searchFriendsByNickname(String nickname) {
        List<User> users = userRepository.findByNicknameContain(nickname);

        return users.stream()
                .map(User::getId)
                .map(userService::retrieveProfile)
                .toList();
    }
}
