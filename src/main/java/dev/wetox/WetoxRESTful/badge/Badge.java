package dev.wetox.WetoxRESTful.badge;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Badge {
    @Id
    @GeneratedValue
    @Column(name = "badge_id")
    private Long id;

    private String name;
}

