package dev.wetox.WetoxRESTful.screentime;

import dev.wetox.WetoxRESTful.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToMany(mappedBy = "screenTime")
    private List<AppScreenTime> appScreenTimes = new ArrayList<>();
}
