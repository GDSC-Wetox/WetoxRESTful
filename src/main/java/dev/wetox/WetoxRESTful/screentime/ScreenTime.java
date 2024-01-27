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

    private Double totalDuration = 0.0;

    @OneToMany(mappedBy = "screenTime", cascade = ALL)
    private List<CategoryScreenTime> categoryScreenTimes = new ArrayList<>();

    public static ScreenTime build(User user, List<CategoryScreenTimeRequest> categoryScreenTimes) {
        ScreenTime screenTime = new ScreenTime();
        screenTime.user = user;
        screenTime.updatedDate = LocalDateTime.now();
        for (CategoryScreenTimeRequest categoryScreenTime: categoryScreenTimes) {
            screenTime.categoryScreenTimes.add(CategoryScreenTime.build(screenTime, categoryScreenTime));
        }
        screenTime.totalDuration = screenTime.categoryScreenTimes.stream()
                .mapToDouble(CategoryScreenTime::getDuration)
                .sum();
        return screenTime;
    }
}
