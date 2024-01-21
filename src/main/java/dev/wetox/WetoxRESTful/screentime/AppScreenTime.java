package dev.wetox.WetoxRESTful.screentime;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class AppScreenTime {
    @Id
    @GeneratedValue
    @Column(name = "app_screen_time_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "screen_time_id")
    private ScreenTime screenTime;

    private String name;

    private String category;

    private Double duration;

    public static AppScreenTime build(ScreenTime screenTime, AppScreenTimeRequest appScreenTimeRequest) {
        AppScreenTime appScreenTime = new AppScreenTime();
        appScreenTime.screenTime = screenTime;
        appScreenTime.name = appScreenTimeRequest.getName();
        appScreenTime.category = appScreenTimeRequest.getCategory();
        appScreenTime.duration = appScreenTimeRequest.getDuration();
        return appScreenTime;
    }
}
