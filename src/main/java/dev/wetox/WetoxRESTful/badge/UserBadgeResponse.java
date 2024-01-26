package dev.wetox.WetoxRESTful.badge;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserBadgeResponse {
    private String badgeName;
    private LocalDateTime rewardedDate;

    public UserBadgeResponse(UserBadge userBadge) {
        this.badgeName = userBadge.getBadge().getName();
        this.rewardedDate = userBadge.getRewardedDate();
    }
}
