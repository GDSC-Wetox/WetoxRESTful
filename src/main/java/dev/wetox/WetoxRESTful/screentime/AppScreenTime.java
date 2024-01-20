package dev.wetox.WetoxRESTful.screentime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
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
}
