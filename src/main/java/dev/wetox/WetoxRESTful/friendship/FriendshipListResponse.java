package dev.wetox.WetoxRESTful.friendship;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@AllArgsConstructor
public class FriendshipListResponse {

    private Long userId;
    private String nickname;
    private Long totalDuration;
    private String profileImage;
}
