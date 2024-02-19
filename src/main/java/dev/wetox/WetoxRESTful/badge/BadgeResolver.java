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

    NO_SCREEN_TIME_ONE_MONTH(new BadgeResolvable() {
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
                if (screenTime.getTotalDuration() != 0L)
                    return false;
                lastDate = LocalDateTime.of(screenTime.getUpdatedDate().toLocalDate(), LocalTime.MIDNIGHT);
            }
            return lastDate.isEqual(LocalDateTime.of(LocalDateTime.now().minusDays(30).toLocalDate(), LocalTime.MIDNIGHT));
        }
    }),

    NO_SCREEN_TIME_AT_LATE_NIGHT(new BadgeResolvable() {
        @Override
        public boolean resolve(User user, ScreenTimeRepository screenTimeRepository, BadgeRepository badgeRepository, UserBadgeRepository userBadgeRepository) {
            LocalTime lateNightStart = LocalTime.of(22, 0);
            LocalTime lateNightEnd = LocalTime.of(2, 0);

            LocalDate today = LocalDate.now();
            LocalDate yesterday = today.minusDays(1);

            List<ScreenTime> twoDaysNightScreenTimes = screenTimeRepository.findScreenTimeByUserId(user.getId());

            //어제 기록 없을 경우 false 반환
            boolean hasRecordsYesterday = twoDaysNightScreenTimes.stream()
                    .anyMatch(st -> st.getUpdatedDate().toLocalDate().equals(yesterday));

            if (!hasRecordsYesterday) {
                return false;
            }

            return twoDaysNightScreenTimes.stream()
                    .filter(st -> st.getUpdatedDate().toLocalDate().equals(today) || st.getUpdatedDate().toLocalDate().equals(yesterday))
                    .noneMatch(st -> {
                        LocalDateTime updatedDateTime = st.getUpdatedDate();
                        LocalTime screenTime = updatedDateTime.toLocalTime();
                        LocalDate screenDate = updatedDateTime.toLocalDate();

                        boolean isLateNightYesterday = screenDate.equals(yesterday) && (screenTime.isAfter(lateNightStart) || screenTime.equals(lateNightStart));
                        boolean isEarlyMorningToday = screenDate.equals(today) && screenTime.isBefore(lateNightEnd);

                        return isLateNightYesterday || isEarlyMorningToday;
                    });
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

    public boolean resolve(User user, ScreenTimeRepository screenTimeRepository, BadgeRepository badgeRepository, UserBadgeRepository userBadgeRepository) {
        return badgeResolvable.resolve(user, screenTimeRepository, badgeRepository, userBadgeRepository);
    }
}
