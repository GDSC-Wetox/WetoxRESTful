package dev.wetox.WetoxRESTful.badge;

import dev.wetox.WetoxRESTful.screentime.ScreenTimeRepository;
import dev.wetox.WetoxRESTful.user.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BadgeResolver {
    WELCOME(new BadgeResolvable() {
        @Override
        public boolean resolve(User user, ScreenTimeRepository screenTimeRepository, BadgeRepository badgeRepository, UserBadgeRepository userBadgeRepository) {
            return true;
        }
    }),
    NO_SCREEN_TIME_ONE_DAY(new BadgeResolvable() {
        @Override
        public boolean resolve(User user, ScreenTimeRepository screenTimeRepository, BadgeRepository badgeRepository, UserBadgeRepository userBadgeRepository) {
            return false;
        }
    }),
    NO_SCREEN_TIME_ONE_WEEK(new BadgeResolvable() {
        @Override
        public boolean resolve(User user, ScreenTimeRepository screenTimeRepository, BadgeRepository badgeRepository, UserBadgeRepository userBadgeRepository) {
            return false;
        }
    }),
    ;

    private final BadgeResolvable badgeResolvable;

    public boolean resolve(User user, ScreenTimeRepository screenTimeRepository, BadgeRepository badgeRepository, UserBadgeRepository userBadgeRepository) {
        return badgeResolvable.resolve(user, screenTimeRepository, badgeRepository, userBadgeRepository);
    }
}
