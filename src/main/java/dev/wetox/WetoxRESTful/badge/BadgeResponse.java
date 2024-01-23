package dev.wetox.WetoxRESTful.badge;

import dev.wetox.WetoxRESTful.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BadgeResponse {
    private String badgeName;
    private boolean isRewarded;
    private LocalDateTime rewardedDate;

    public static BadgeResponse buildRewarded(UserBadge userBadge) {
        BadgeResponse badgeResponse = new BadgeResponse();
        badgeResponse.badgeName = userBadge.getBadge().getName();
        badgeResponse.isRewarded = true;
        badgeResponse.rewardedDate = userBadge.getRewardedDate();
        return badgeResponse;
    }

    public static BadgeResponse buildNotRewarded(Badge badge) {
        BadgeResponse badgeResponse = new BadgeResponse();
        badgeResponse.badgeName = badge.getName();
        badgeResponse.isRewarded = false;
        badgeResponse.rewardedDate = null;
        return badgeResponse;
    }
}
