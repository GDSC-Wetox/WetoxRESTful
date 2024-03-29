package dev.wetox.WetoxRESTful.badge;

import dev.wetox.WetoxRESTful.screentime.Category;
import dev.wetox.WetoxRESTful.screentime.CategoryScreenTime;
import dev.wetox.WetoxRESTful.screentime.ScreenTime;
import dev.wetox.WetoxRESTful.screentime.ScreenTimeRepository;
import dev.wetox.WetoxRESTful.user.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public enum BadgeResolver {

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

    NO_GAME_ONE_MONTH(new BadgeResolvable() {
        @Override
        public boolean resolve(User user, ScreenTimeRepository screenTimeRepository, BadgeRepository badgeRepository, UserBadgeRepository userBadgeRepository) {
            List<ScreenTime> lastWeekScreenTimes = screenTimeRepository.findByDateDuration(
                    user.getId(),
                    LocalDateTime.of(LocalDateTime.now().minusDays(30).toLocalDate(), LocalTime.MIDNIGHT),
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
            return lastDate.isEqual(LocalDateTime.of(LocalDateTime.now().minusDays(30).toLocalDate(), LocalTime.MIDNIGHT));
        }
    }),

    NO_YOUTUBE_ONE_DAY(new BadgeResolvable() {
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
            return getEntertainementDuration(latestScreenTimeOfLastDay) == 0L;
        }
    }),

    NO_YOUTUBE_ONE_WEEK(new BadgeResolvable() {
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
                if (getEntertainementDuration(screenTime) != 0L)
                    return false;
                lastDate = LocalDateTime.of(screenTime.getUpdatedDate().toLocalDate(), LocalTime.MIDNIGHT);
            }
            return lastDate.isEqual(LocalDateTime.of(LocalDateTime.now().minusDays(7).toLocalDate(), LocalTime.MIDNIGHT));
        }
    }),

    NO_YOUTUBE_ONE_MONTH(new BadgeResolvable() {
        @Override
        public boolean resolve(User user, ScreenTimeRepository screenTimeRepository, BadgeRepository badgeRepository, UserBadgeRepository userBadgeRepository) {
            List<ScreenTime> lastWeekScreenTimes = screenTimeRepository.findByDateDuration(
                    user.getId(),
                    LocalDateTime.of(LocalDateTime.now().minusDays(30).toLocalDate(), LocalTime.MIDNIGHT),
                    LocalDateTime.of(LocalDateTime.now().toLocalDate(), LocalTime.MIDNIGHT)
            );
            if (lastWeekScreenTimes.isEmpty())
                return false;
            LocalDateTime lastDate = null;
            for (ScreenTime screenTime: lastWeekScreenTimes) {
                if (lastDate != null && screenTime.getUpdatedDate().isAfter(lastDate))
                    continue;
                if (getEntertainementDuration(screenTime) != 0L)
                    return false;
                lastDate = LocalDateTime.of(screenTime.getUpdatedDate().toLocalDate(), LocalTime.MIDNIGHT);
            }
            return lastDate.isEqual(LocalDateTime.of(LocalDateTime.now().minusDays(30).toLocalDate(), LocalTime.MIDNIGHT));
        }
    }),

    NO_SOCIAL_MEDIA_ONE_DAY(new BadgeResolvable() {
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
            return getSocialMediaDuration(latestScreenTimeOfLastDay) == 0L;
        }
    }),

    NO_SOCIAL_MEDIA_ONE_WEEK(new BadgeResolvable() {
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
                if (getSocialMediaDuration(screenTime) != 0L)
                    return false;
                lastDate = LocalDateTime.of(screenTime.getUpdatedDate().toLocalDate(), LocalTime.MIDNIGHT);
            }
            return lastDate.isEqual(LocalDateTime.of(LocalDateTime.now().minusDays(7).toLocalDate(), LocalTime.MIDNIGHT));
        }
    }),

    NO_SOCIAL_MEDIA_ONE_MONTH(new BadgeResolvable() {
        @Override
        public boolean resolve(User user, ScreenTimeRepository screenTimeRepository, BadgeRepository badgeRepository, UserBadgeRepository userBadgeRepository) {
            List<ScreenTime> lastWeekScreenTimes = screenTimeRepository.findByDateDuration(
                    user.getId(),
                    LocalDateTime.of(LocalDateTime.now().minusDays(30).toLocalDate(), LocalTime.MIDNIGHT),
                    LocalDateTime.of(LocalDateTime.now().toLocalDate(), LocalTime.MIDNIGHT)
            );
            if (lastWeekScreenTimes.isEmpty())
                return false;
            LocalDateTime lastDate = null;
            for (ScreenTime screenTime: lastWeekScreenTimes) {
                if (lastDate != null && screenTime.getUpdatedDate().isAfter(lastDate))
                    continue;
                if (getSocialMediaDuration(screenTime) != 0L)
                    return false;
                lastDate = LocalDateTime.of(screenTime.getUpdatedDate().toLocalDate(), LocalTime.MIDNIGHT);
            }
            return lastDate.isEqual(LocalDateTime.of(LocalDateTime.now().minusDays(30).toLocalDate(), LocalTime.MIDNIGHT));
        }
    }),



    INFORMATION_AND_BOOK_ONE_DAY(new BadgeResolvable() {
        @Override
        public boolean resolve(User user, ScreenTimeRepository screenTimeRepository, BadgeRepository badgeRepository, UserBadgeRepository userBadgeRepository) {
            List<ScreenTime> lastDayScreenTimes = screenTimeRepository.findByDateDuration(
                    user.getId(),
                    LocalDateTime.of(LocalDateTime.now().minusDays(1).toLocalDate(), LocalTime.MIDNIGHT),
                    LocalDateTime.of(LocalDateTime.now().toLocalDate(), LocalTime.MIDNIGHT)
            );

            if (lastDayScreenTimes.isEmpty())
                return false;

            for (ScreenTime screenTime : lastDayScreenTimes) {
                if (getBookDuration(screenTime) > 0) {
                    return true;
                }
            }
            return false;
        }
    }),

    INFORMATION_AND_BOOK_ONE_WEEK(new BadgeResolvable() {
        @Override
        public boolean resolve(User user, ScreenTimeRepository screenTimeRepository, BadgeRepository badgeRepository, UserBadgeRepository userBadgeRepository) {
            List<ScreenTime> lastWeekScreenTimes = screenTimeRepository.findByDateDuration(
                    user.getId(),
                    LocalDateTime.of(LocalDateTime.now().minusWeeks(1).toLocalDate(), LocalTime.MIDNIGHT),
                    LocalDateTime.of(LocalDateTime.now().toLocalDate(), LocalTime.MIDNIGHT)
            );

            if (lastWeekScreenTimes.isEmpty())
                return false;

            Map<LocalDate, Long> dailyDurations = new HashMap<>();

            for (ScreenTime screenTime : lastWeekScreenTimes) {
                LocalDate date = screenTime.getUpdatedDate().toLocalDate();
                long duration = getBookDuration(screenTime);

                dailyDurations.put(date, dailyDurations.getOrDefault(date, 0L) + duration);
            }

            LocalDate date = LocalDate.now().minusDays(7);

            for (int i = 0; i < 7; i++) {
                if (!dailyDurations.containsKey(date) || dailyDurations.get(date) == 0)
                    return false;
                date = date.plusDays(1);
            }
            return true;
        }
    }),

    INFORMATION_AND_BOOK_ONE_MONTH(new BadgeResolvable() {
        @Override
        public boolean resolve(User user, ScreenTimeRepository screenTimeRepository, BadgeRepository badgeRepository, UserBadgeRepository userBadgeRepository) {
            List<ScreenTime> lastMonthScreenTimes = screenTimeRepository.findByDateDuration(
                    user.getId(),
                    LocalDateTime.of(LocalDateTime.now().minusDays(30).toLocalDate(), LocalTime.MIDNIGHT),
                    LocalDateTime.of(LocalDateTime.now().toLocalDate(), LocalTime.MIDNIGHT)
            );

            if (lastMonthScreenTimes.isEmpty())
                return false;

            Map<LocalDate, Long> dailyDurations = new HashMap<>();

            for (ScreenTime screenTime : lastMonthScreenTimes) {
                LocalDate date = screenTime.getUpdatedDate().toLocalDate();
                long duration = getBookDuration(screenTime);

                dailyDurations.put(date, dailyDurations.getOrDefault(date, 0L) + duration);
            }

            LocalDate date = LocalDate.now().minusDays(30);
            for (int i = 0; i < 30; i++) {
                if (!dailyDurations.containsKey(date) || dailyDurations.get(date) == 0)
                    return false;
                date = date.plusDays(1);
            }
            return true;
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

    private static Long getBookDuration(ScreenTime screenTime) {
        return screenTime.getCategoryScreenTimes().stream()
                .filter(c -> c.getCategory() == Category.INFORMATION_AND_BOOK)
                .map(CategoryScreenTime::getDuration)
                .reduce(0L, Long::sum);
    }

    private static Long getEntertainementDuration(ScreenTime screenTime) {
        return screenTime.getCategoryScreenTimes().stream()
                .filter(c -> c.getCategory() == Category.ENTERTAINEMENT)
                .map(CategoryScreenTime::getDuration)
                .reduce(0L, Long::sum);
    }

    private static Long getSocialMediaDuration(ScreenTime screenTime) {
        return screenTime.getCategoryScreenTimes().stream()
                .filter(c -> c.getCategory() == Category.SOCIAL_MEDIA)
                .map(CategoryScreenTime::getDuration)
                .reduce(0L, Long::sum);
    }

    public boolean resolve(User user, ScreenTimeRepository screenTimeRepository, BadgeRepository badgeRepository, UserBadgeRepository userBadgeRepository) {
        return badgeResolvable.resolve(user, screenTimeRepository, badgeRepository, userBadgeRepository);
    }
}
