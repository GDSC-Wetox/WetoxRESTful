package dev.wetox.WetoxRESTful.screentime;

import dev.wetox.WetoxRESTful.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.PERSIST;
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

    @OneToMany(mappedBy = "screenTime", cascade = PERSIST)
    private List<AppScreenTime> appScreenTimes = new ArrayList<>();

    public static ScreenTime build(User user, List<AppScreenTimeRequest> appScreenTimes) {
        ScreenTime screenTime = new ScreenTime();
        screenTime.user = user;
        screenTime.updatedDate = LocalDateTime.now();

        double totalDuration = 0.0;
        for (AppScreenTimeRequest app: appScreenTimes) {
            AppScreenTime appScreenTime = AppScreenTime.builder()
                    .screenTime(screenTime)
                    .name(app.getName())
                    .category(app.getCategory())
                    .duration(app.getDuration())
                    .build();
            totalDuration += app.getDuration();
            screenTime.getAppScreenTimes().add(appScreenTime);
        }
        screenTime.totalDuration = totalDuration;

        return screenTime;
    }
}
