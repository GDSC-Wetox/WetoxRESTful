package dev.wetox.WetoxRESTful.user;

import dev.wetox.WetoxRESTful.exception.MemberNotFoundException;
import dev.wetox.WetoxRESTful.image.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final ImageService imageService;

    public UserResponse retrieveProfile(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(MemberNotFoundException::new);
        return UserResponse.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .profileImage(imageService.getImageUrl(user.getProfileImageUUID()))
                .build();
    }

    public UserDuplicatedConfirmResponse checkNicknameDuplicated(String nickname) {
        Optional<User> user = userRepository.findByNickname(nickname);
        return UserDuplicatedConfirmResponse.build(user.isPresent());
    }

    public List<UserResponse> searchFriendsByNickname(String nickname) {
        List<User> users = userRepository.findByNicknameContain(nickname);

        log.info(nickname);

        return users.stream()
                .map(User::getId)
                .map(this::retrieveProfile)
                .collect(Collectors.toList());
    }
}
