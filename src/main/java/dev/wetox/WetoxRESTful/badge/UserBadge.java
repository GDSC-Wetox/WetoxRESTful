package dev.wetox.WetoxRESTful.badge;

import dev.wetox.WetoxRESTful.user.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
public class UserBadge {
    @Id
    @GeneratedValue
    @Column(name = "user_badge_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "badge_id")
    private Badge badge;

    private LocalDateTime rewardedDate;

    public static UserBadge build(User user, Badge badge) {
        UserBadge userBadge = new UserBadge();
        userBadge.user = user;
        userBadge.badge = badge;
        userBadge.rewardedDate = LocalDateTime.now();
        return userBadge;
    }
}

