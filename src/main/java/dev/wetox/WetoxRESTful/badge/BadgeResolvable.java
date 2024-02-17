package dev.wetox.WetoxRESTful.badge;

import dev.wetox.WetoxRESTful.screentime.ScreenTimeRepository;
import dev.wetox.WetoxRESTful.user.User;

public interface BadgeResolvable {
    boolean resolve(User user, ScreenTimeRepository screenTimeRepository, BadgeRepository badgeRepository, UserBadgeRepository userBadgeRepository);
}
