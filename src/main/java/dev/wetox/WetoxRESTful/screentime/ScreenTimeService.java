package dev.wetox.WetoxRESTful.screentime;

import dev.wetox.WetoxRESTful.exception.MemberNotFoundException;
import dev.wetox.WetoxRESTful.exception.ScreenTimeNotFoundException;
import dev.wetox.WetoxRESTful.user.User;
import dev.wetox.WetoxRESTful.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScreenTimeService {
    private final UserRepository userRepository;
    private final ScreenTimeRepository screenTimeRepository;
    private final AppScreenTimeRepository appScreenTimeRepository;

    @Transactional
    public ScreenTimeResponse updateScreenTime(Long userId, List<AppScreenTimeRequest> request) {
        User user = userRepository.findById(userId).orElseThrow(MemberNotFoundException::new);

        ScreenTime screenTime = new ScreenTime(); // builder를 사용하면 list 필드가 null이 됨;;
        screenTime.setUser(user);
        screenTime.setUpdatedDate(LocalDateTime.now());
        screenTimeRepository.save(screenTime);

        double totalDuration = 0.0;
        for (AppScreenTimeRequest appRequest: request) {
            AppScreenTime appScreenTime = AppScreenTime.builder()
                    .screenTime(screenTime)
                    .name(appRequest.getName())
                    .category(appRequest.getCategory())
                    .duration(appRequest.getDuration())
                    .build();
            totalDuration += appRequest.getDuration();
            screenTime.getAppScreenTimes().add(appScreenTime);
            appScreenTimeRepository.save(appScreenTime);
        }
        screenTime.setTotalDuration(totalDuration);

        return buildScreenTimeResponse(user, screenTime);
    }

    public ScreenTimeResponse retrieveScreenTime(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(MemberNotFoundException::new);
        ScreenTime screenTime = screenTimeRepository.findLatestByUserId(userId).orElseThrow(ScreenTimeNotFoundException::new);
        return buildScreenTimeResponse(user, screenTime);
    }

    private ScreenTimeResponse buildScreenTimeResponse(User user, ScreenTime screenTime) {
        return ScreenTimeResponse.builder()
                .nickname(user.getNickname())
                .updatedDate(screenTime.getUpdatedDate())
                .totalDuration(screenTime.getTotalDuration())
                .appScreenTimes(
                        screenTime.getAppScreenTimes().stream()
                                .map(AppScreenTimeResponse::new)
                                .toList())
                .build();
    }
}
