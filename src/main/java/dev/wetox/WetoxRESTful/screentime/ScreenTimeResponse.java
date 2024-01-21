package dev.wetox.WetoxRESTful.screentime;

import dev.wetox.WetoxRESTful.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScreenTimeResponse {
    private String nickname;
    private LocalDateTime updatedDate;
    private Double totalDuration;
    private List<AppScreenTimeResponse> appScreenTimes;

    public static ScreenTimeResponse build(User user, ScreenTime screenTime) {
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
