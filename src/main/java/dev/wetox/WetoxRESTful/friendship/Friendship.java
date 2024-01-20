package dev.wetox.WetoxRESTful.friendship;

import dev.wetox.WetoxRESTful.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class Friendship {
    @Id @GeneratedValue
    @Column(name = "friendship_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "from_id")
    private User from;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "to_id")
    private User to;

    @Enumerated(STRING)
    private FriendshipStatus status;
}

