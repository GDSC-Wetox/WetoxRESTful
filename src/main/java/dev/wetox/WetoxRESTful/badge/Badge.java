package dev.wetox.WetoxRESTful.badge;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Getter
@Setter
public class Badge {
    @Id
    @GeneratedValue
    @Column(name = "badge_id")
    private Long id;

    private String name;

    @Enumerated(STRING)
    private BadgeResolver badgeResolver;
}
