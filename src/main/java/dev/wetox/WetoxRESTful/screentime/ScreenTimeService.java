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
    public List<AppScreenTimeResponse> updateScreenTime(Long userId, List<AppScreenTimeRequst> request) {
        return null;
    }

    public List<AppScreenTimeResponse> findScreenTime(Long userId) {
        return null;
    }
}
