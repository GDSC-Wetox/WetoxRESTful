package dev.wetox.WetoxRESTful.Friend;

import dev.wetox.WetoxRESTful.user.User;
import dev.wetox.WetoxRESTful.user.UserRepository;
import dev.wetox.WetoxRESTful.Friend.FriendRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
@NoArgsConstructor
class FriendTest {

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private UserRepository userRepository;

    private User fromUserId;
    private User toUserId;


    @BeforeEach
    void beforeEach() {
        fromUserId = userRepository.save(User.builder()
                .nickname("from_id")
                .build());

        toUserId = userRepository.save(User.builder()
                .nickname("to_id")
                .build());
        userRepository.save(fromUserId);
        userRepository.save(toUserId);
    }

    @AfterEach
    void afterEach(){
        friendRepository.deleteAll();
    }

    @Test
    void createFriendTest() {

        FriendId friendId = FriendId.builder()
                .fromUserId(fromUserId.getId())
                .toUserId(toUserId.getId())
                .build();

        friendRepository.save(Friend.builder()
                .id(friendId)
                .build());

    }
}