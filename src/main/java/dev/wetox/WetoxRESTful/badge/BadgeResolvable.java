package dev.wetox.WetoxRESTful.badge;

import dev.wetox.WetoxRESTful.user.User;

public interface BadgeResolvable {
    boolean resolve(User user, BadgeRepository badgeRepository, UserBadgeRepository userBadgeRepository);
}
