package dev.wetox.WetoxRESTful.friendship;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class FriendshipCreateResponse {

    private Long friendshipId;
    private Long from;
    private Long to;
    private LocalDateTime createDate;
    private LocalDateTime requestDate;
    private Enum status;

    public static FriendshipCreateResponse from(Friendship friendship) {
        return FriendshipCreateResponse.builder()
                .friendshipId(friendship.getId())
                .from(friendship.getFrom().getId())
                .to(friendship.getTo().getId())
                .createDate(friendship.getCreatedAt())
                .requestDate(friendship.getCreatedAt())
                .status(friendship.getStatus())
                .build();

    }

}
