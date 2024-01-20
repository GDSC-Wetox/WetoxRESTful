package dev.wetox.WetoxRESTful.screentime;

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
}
