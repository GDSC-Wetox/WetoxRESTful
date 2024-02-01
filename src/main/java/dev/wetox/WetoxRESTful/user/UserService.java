package dev.wetox.WetoxRESTful.user;

import dev.wetox.WetoxRESTful.exception.MemberNotFoundException;
import dev.wetox.WetoxRESTful.image.ImageService;
import dev.wetox.WetoxRESTful.screentime.ScreenTime;
import dev.wetox.WetoxRESTful.screentime.ScreenTimeRepository;
import dev.wetox.WetoxRESTful.screentime.ScreenTimeResponse;
import dev.wetox.WetoxRESTful.screentime.ScreenTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}
