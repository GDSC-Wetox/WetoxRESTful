package dev.wetox.WetoxRESTful.badge;

import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Getter
public class Badge {
    @Id
    @GeneratedValue
    @Column(name = "badge_id")
    private Long id;

    private String name;

    @Enumerated(STRING)
    private BadgeResolver badgeResolver;

    public static Badge build(BadgeResolver badgeResolver) {
        Badge badge = new Badge();
        badge.name = badgeResolver.name();
        badge.badgeResolver = badgeResolver;
        return badge;
    }
}
