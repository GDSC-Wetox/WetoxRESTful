package dev.wetox.WetoxRESTful.friendship;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class FriendshipAcceptResponse {

    private Long friendshipId;
    private Long from;
    private Long to;
    private LocalDateTime createDate;
    private LocalDateTime requestDate;
    private Enum status;

    public static FriendshipAcceptResponse from(Friendship friendship) {
        return FriendshipAcceptResponse.builder()
                .friendshipId(friendship.getId())
                .from(friendship.getFrom().getId())
                .to(friendship.getTo().getId())
                .createDate(friendship.getCreatedAt())
                .requestDate(friendship.getCreatedAt())
                .status(friendship.getStatus())
                .build();

    }
}
