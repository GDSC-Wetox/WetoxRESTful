package dev.wetox.WetoxRESTful.screentime;

import dev.wetox.WetoxRESTful.exception.UserNotFoundException;
import dev.wetox.WetoxRESTful.user.User;
import dev.wetox.WetoxRESTful.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScreenTimeService {
    private final UserRepository userRepository;
    private final ScreenTimeRepository screenTimeRepository;

    @Transactional
    public ScreenTimeResponse updateScreenTime(Long userId, List<CategoryScreenTimeRequest> request) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        ScreenTime screenTime = ScreenTime.build(user, request);
        screenTimeRepository.save(screenTime);
        return ScreenTimeResponse.build(user, screenTime);
    }

    public ScreenTimeResponse retrieveScreenTime(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        Page<ScreenTime> screenTimePage
                = screenTimeRepository.findLatestByUserId(userId, PageRequest.of(0, 1));
        List<ScreenTime> screenTimes = screenTimePage.getContent();
        if (screenTimes.isEmpty()) {
            return ScreenTimeResponse.build(user, new ScreenTime());
        }
        return ScreenTimeResponse.build(user, screenTimes.get(0));
    }
}
