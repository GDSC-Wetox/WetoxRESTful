package dev.wetox.WetoxRESTful.badge;

import dev.wetox.WetoxRESTful.screentime.ScreenTime;
import dev.wetox.WetoxRESTful.screentime.ScreenTimeRepository;
import dev.wetox.WetoxRESTful.user.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public enum BadgeResolver {

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
    ;

    private final BadgeResolvable badgeResolvable;

    public boolean resolve(User user, ScreenTimeRepository screenTimeRepository, BadgeRepository badgeRepository, UserBadgeRepository userBadgeRepository) {
        return badgeResolvable.resolve(user, screenTimeRepository, badgeRepository, userBadgeRepository);
    }
}
