package dev.wetox.WetoxRESTful.badge;

import dev.wetox.WetoxRESTful.screentime.Category;
import dev.wetox.WetoxRESTful.screentime.CategoryScreenTime;
import dev.wetox.WetoxRESTful.screentime.ScreenTime;
import dev.wetox.WetoxRESTful.screentime.ScreenTimeRepository;
import dev.wetox.WetoxRESTful.user.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public enum BadgeResolver {

    NO_SCREEN_TIME_ONE_DAY(new BadgeResolvable() {
        @Override
        public boolean resolve(User user, ScreenTimeRepository screenTimeRepository, BadgeRepository badgeRepository, UserBadgeRepository userBadgeRepository) {
            List<ScreenTime> lastDayScreenTimes = screenTimeRepository.findByDateDuration(
                    user.getId(),
                    LocalDateTime.of(LocalDateTime.now().minusDays(1).toLocalDate(), LocalTime.MIDNIGHT),
                    LocalDateTime.of(LocalDateTime.now().toLocalDate(), LocalTime.MIDNIGHT)
            );
            if (lastDayScreenTimes.isEmpty())
                return false;
            ScreenTime latestScreenTimeOfLastDay = lastDayScreenTimes.get(0);
            return latestScreenTimeOfLastDay.getTotalDuration() == 0L;
        }
    }),

    NO_SCREEN_TIME_ONE_WEEK(new BadgeResolvable() {
        @Override
        public boolean resolve(User user, ScreenTimeRepository screenTimeRepository, BadgeRepository badgeRepository, UserBadgeRepository userBadgeRepository) {
            List<ScreenTime> lastWeekScreenTimes = screenTimeRepository.findByDateDuration(
                    user.getId(),
                    LocalDateTime.of(LocalDateTime.now().minusDays(7).toLocalDate(), LocalTime.MIDNIGHT),
                    LocalDateTime.of(LocalDateTime.now().toLocalDate(), LocalTime.MIDNIGHT)
            );
            if (lastWeekScreenTimes.isEmpty())
                return false;
            LocalDateTime lastDate = null;
            for (ScreenTime screenTime: lastWeekScreenTimes) {
                if (lastDate != null && screenTime.getUpdatedDate().isAfter(lastDate))
                    continue;
                if (screenTime.getTotalDuration() != 0L)
                    return false;
                lastDate = LocalDateTime.of(screenTime.getUpdatedDate().toLocalDate(), LocalTime.MIDNIGHT);
            }
            return lastDate.isEqual(LocalDateTime.of(LocalDateTime.now().minusDays(7).toLocalDate(), LocalTime.MIDNIGHT));
        }
    }),

    NO_GAME_ONE_DAY(new BadgeResolvable() {
        @Override
        public boolean resolve(User user, ScreenTimeRepository screenTimeRepository, BadgeRepository badgeRepository, UserBadgeRepository userBadgeRepository) {
            List<ScreenTime> lastDayScreenTimes = screenTimeRepository.findByDateDuration(
                    user.getId(),
                    LocalDateTime.of(LocalDateTime.now().minusDays(1).toLocalDate(), LocalTime.MIDNIGHT),
                    LocalDateTime.of(LocalDateTime.now().toLocalDate(), LocalTime.MIDNIGHT)
            );
            if (lastDayScreenTimes.isEmpty())
                return false;
            ScreenTime latestScreenTimeOfLastDay = lastDayScreenTimes.get(0);
            return getGameDuration(latestScreenTimeOfLastDay) == 0L;
        }
    }),

    NO_GAME_ONE_WEEK(new BadgeResolvable() {
        @Override
        public boolean resolve(User user, ScreenTimeRepository screenTimeRepository, BadgeRepository badgeRepository, UserBadgeRepository userBadgeRepository) {
            List<ScreenTime> lastWeekScreenTimes = screenTimeRepository.findByDateDuration(
                    user.getId(),
                    LocalDateTime.of(LocalDateTime.now().minusDays(7).toLocalDate(), LocalTime.MIDNIGHT),
                    LocalDateTime.of(LocalDateTime.now().toLocalDate(), LocalTime.MIDNIGHT)
            );
            if (lastWeekScreenTimes.isEmpty())
                return false;
            LocalDateTime lastDate = null;
            for (ScreenTime screenTime: lastWeekScreenTimes) {
                if (lastDate != null && screenTime.getUpdatedDate().isAfter(lastDate))
                    continue;
                if (getGameDuration(screenTime) != 0L)
                    return false;
                lastDate = LocalDateTime.of(screenTime.getUpdatedDate().toLocalDate(), LocalTime.MIDNIGHT);
            }
            return lastDate.isEqual(LocalDateTime.of(LocalDateTime.now().minusDays(7).toLocalDate(), LocalTime.MIDNIGHT));
        }
    }),

    ;

    private final BadgeResolvable badgeResolvable;

    private static Long getGameDuration(ScreenTime screenTime) {
        return screenTime.getCategoryScreenTimes().stream()
                .filter(c -> c.getCategory() == Category.GAME)
                .map(CategoryScreenTime::getDuration)
                .reduce(0L, Long::sum);
    }

    public boolean resolve(User user, ScreenTimeRepository screenTimeRepository, BadgeRepository badgeRepository, UserBadgeRepository userBadgeRepository) {
        return badgeResolvable.resolve(user, screenTimeRepository, badgeRepository, userBadgeRepository);
    }
}
