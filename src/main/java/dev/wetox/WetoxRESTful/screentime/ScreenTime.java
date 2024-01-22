package dev.wetox.WetoxRESTful.screentime;

import dev.wetox.WetoxRESTful.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class ScreenTime {
    @Id
    @GeneratedValue
    @Column(name = "screen_time_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime updatedDate;

    private Double totalDuration;

    @OneToMany(mappedBy = "screenTime", cascade = ALL)
    private List<AppScreenTime> appScreenTimes = new ArrayList<>();

    public static ScreenTime build(User user, List<AppScreenTimeRequest> appScreenTimes) {
        ScreenTime screenTime = new ScreenTime();
        screenTime.user = user;
        screenTime.updatedDate = LocalDateTime.now();
        for (AppScreenTimeRequest appScreenTime: appScreenTimes) {
            screenTime.appScreenTimes.add(AppScreenTime.build(screenTime, appScreenTime));
        }
        screenTime.totalDuration = screenTime.appScreenTimes.stream()
                .mapToDouble(AppScreenTime::getDuration)
                .sum();
        return screenTime;
    }
}
