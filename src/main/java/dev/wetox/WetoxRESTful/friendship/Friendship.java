package dev.wetox.WetoxRESTful.friendship;

import dev.wetox.WetoxRESTful.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Friendship {
    @Id
    @GeneratedValue
    @Column(name = "friendship_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "from_id")
    private User from;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "to_id")
    private User to;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @Enumerated(STRING)
    private FriendshipStatus status;

    @Builder
    private Friendship(Long id, User from, User to, FriendshipStatus status) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.status = status;
    }

     //친구 요청
    public static Friendship create(User toUser, User fromUser) {
        return Friendship.builder()
                .from(fromUser)
                .to(toUser)
                .status(FriendshipStatus.REQUEST)
                .build();
    }

    // 친구 요청 수락
    public void accept() {this.status = FriendshipStatus.ACCEPT;}
}

