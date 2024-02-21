package dev.wetox.WetoxRESTful.friendship;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;


@Getter
@Builder
@AllArgsConstructor
public class FriendshipListResponse {
    private List<FriendshipResponse> friendRequestsList;
}
