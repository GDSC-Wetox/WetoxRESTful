package dev.wetox.WetoxRESTful.user;

import dev.wetox.WetoxRESTful.exception.MemberNotFoundException;
import dev.wetox.WetoxRESTful.image.ImageService;
import dev.wetox.WetoxRESTful.screentime.ScreenTimeResponse;
import dev.wetox.WetoxRESTful.screentime.ScreenTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ImageService imageService;
    private final ScreenTimeService screenTimeService;

    public UserResponse retrieveProfile(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(MemberNotFoundException::new);
        ScreenTimeResponse screenTimeResponse = screenTimeService.retrieveScreenTime(userId);
        return UserResponse.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .totalDuration(screenTimeResponse.getTotalDuration())
                .profileImage(imageService.getImageUrl(user.getProfileImageUUID()))
                .build();
    }

    public UserDuplicatedConfirmResponse checkNicknameDuplicated(String nickname) {
        Optional<User> user = userRepository.findByNickname(nickname);
        return UserDuplicatedConfirmResponse.build(user.isPresent());
    }
}
