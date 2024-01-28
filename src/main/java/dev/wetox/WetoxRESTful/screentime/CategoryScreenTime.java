package dev.wetox.WetoxRESTful.screentime;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class CategoryScreenTime {
    @Id
    @GeneratedValue
    @Column(name = "app_screen_time_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "screen_time_id")
    private ScreenTime screenTime;

    @Enumerated(STRING)
    private Category category;

    private Long duration;

    public static CategoryScreenTime build(ScreenTime screenTime, CategoryScreenTimeRequest categoryScreenTimeRequest) {
        CategoryScreenTime categoryScreenTime = new CategoryScreenTime();
        categoryScreenTime.screenTime = screenTime;
        categoryScreenTime.category = categoryScreenTimeRequest.getCategory();
        categoryScreenTime.duration = categoryScreenTimeRequest.getDuration();
        return categoryScreenTime;
    }
}
