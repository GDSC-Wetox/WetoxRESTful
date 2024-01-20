package dev.wetox.WetoxRESTful.screentime;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScreenTimeService {
    @Transactional
    public ScreenTimeResponse updateScreenTime(Long userId, List<AppScreenTimeRequest> request) {
        return null;
    }

    public ScreenTimeResponse findScreenTime(Long userId) {
        return null;
    }
}
