package dev.wetox.WetoxRESTful.badge;

import dev.wetox.WetoxRESTful.user.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BadgeResolver {
    NO_SCREEN_TIME_ONE_DAY(new BadgeResolvable() {
        @Override
        public boolean resolve(User user) {
            return false;
        }
    }),
    NO_SCREEN_TIME_ONE_WEEK(new BadgeResolvable() {
        @Override
        public boolean resolve(User user) {
            return false;
        }
    }),
    ;

    private final BadgeResolvable badgeResolvable;

    public boolean resolve(User user) {
        return badgeResolvable.resolve(user);
    }
}
