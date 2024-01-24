package dev.wetox.WetoxRESTful.friendship;

import dev.wetox.WetoxRESTful.user.User;
import dev.wetox.WetoxRESTful.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FriendshipService {

    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;

   //친구 관계 요청
    @Transactional
    public FriendshipCreateResponse create(Long fromUserId, Long toUserId) {

        User fromUser = userRepository.findById(fromUserId).orElseThrow();
        User toUser = userRepository.findById(toUserId).orElseThrow();

        friendshipRepository.findByToIdAndFromId(toUserId, fromUserId).ifPresent(friendship -> {
            throw new IllegalStateException("이미 친구관계가 존재합니다.");
        });

        friendshipRepository.findByToIdAndFromId(fromUserId, toUserId).ifPresent(friendship -> {
            throw new IllegalStateException("이미 친구관계가 존재합니다.");
        });


        Friendship friendshipFrom = Friendship.builder()
                    .from(fromUser)
                    .to(toUser)
                    .status(FriendshipStatus.ACCEPT)
                    .build();

        Friendship friendshipTo = Friendship.builder()
                    .from(toUser)
                    .to(fromUser)
                    .status(FriendshipStatus.REQUEST)
                    .build();


        friendshipRepository.save(friendshipFrom);
        friendshipRepository.save(friendshipTo);

        return FriendshipCreateResponse.from(friendshipFrom);
    }

    //친구관계 수락
    @Transactional
    public void accept(Long toId, Long fromId) {
        Friendship friendship = friendshipRepository.findByToIdAndFromId(toId, fromId).orElseThrow();
        friendship.accept();
    }

    public List<FriendshipResponse> findMutualAcceptedFriendships(Long id) {
        return FriendshipResponse.from(friendshipRepository.findMutualAcceptedFriendships(id));
    }

}
