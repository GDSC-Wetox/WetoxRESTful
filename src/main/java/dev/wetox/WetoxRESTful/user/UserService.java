package dev.wetox.WetoxRESTful.user;

import dev.wetox.WetoxRESTful.exception.UserNotFoundException;
import dev.wetox.WetoxRESTful.image.ImageService;
import dev.wetox.WetoxRESTful.screentime.ScreenTimeResponse;
import dev.wetox.WetoxRESTful.screentime.ScreenTimeService;
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
    private final ScreenTimeService screenTimeService;

    public UserResponse retrieveProfile(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        ScreenTimeResponse screenTime = screenTimeService.retrieveScreenTime(userId);
        return UserResponse.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .profileImage(imageService.getImageUrl(user.getProfileImageUUID()))
                .totalDuration(screenTime.getTotalDuration())
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
