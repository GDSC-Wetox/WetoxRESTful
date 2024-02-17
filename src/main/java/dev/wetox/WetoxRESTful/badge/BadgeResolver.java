package dev.wetox.WetoxRESTful.badge;

import dev.wetox.WetoxRESTful.screentime.Category;
import dev.wetox.WetoxRESTful.screentime.CategoryScreenTime;
import dev.wetox.WetoxRESTful.screentime.ScreenTime;
import dev.wetox.WetoxRESTful.screentime.ScreenTimeRepository;
import dev.wetox.WetoxRESTful.user.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public enum BadgeResolver {

    NO_SCREEN_TIME_ONE_DAY(new BadgeResolvable() {
        @Override
        public boolean resolve(User user, ScreenTimeRepository screenTimeRepository, BadgeRepository badgeRepository, UserBadgeRepository userBadgeRepository) {
            Page<ScreenTime> screenTimePage = screenTimeRepository.findLatestByUserId(user.getId(), PageRequest.of(0, 1));
            List<ScreenTime> latestScreenTime = screenTimePage.getContent();
            if (latestScreenTime.isEmpty())
                return false;
            return latestScreenTime.get(0).getUpdatedDate().isBefore(
                    LocalDateTime.of(LocalDateTime.now().toLocalDate(), LocalTime.MIDNIGHT)
            );
        }
    }),

    NO_SCREEN_TIME_ONE_WEEK(new BadgeResolvable() {
        @Override
        public boolean resolve(User user, ScreenTimeRepository screenTimeRepository, BadgeRepository badgeRepository, UserBadgeRepository userBadgeRepository) {
            Page<ScreenTime> screenTimePage = screenTimeRepository.findLatestByUserId(user.getId(), PageRequest.of(0, 1));
            List<ScreenTime> latestScreenTime = screenTimePage.getContent();
            if (latestScreenTime.isEmpty())
                return false;
            return latestScreenTime.get(0).getUpdatedDate().isBefore(
                    LocalDateTime.of(LocalDateTime.now().minusDays(7).toLocalDate(), LocalTime.MIDNIGHT)
            );
        }
    }),

    NO_GAME_ONE_DAY(new BadgeResolvable() {
        @Override
        public boolean resolve(User user, ScreenTimeRepository screenTimeRepository, BadgeRepository badgeRepository, UserBadgeRepository userBadgeRepository) {
            List<ScreenTime> screenTimes = screenTimeRepository.findByDate(
                    user.getId(),
                    LocalDateTime.of(LocalDateTime.now().toLocalDate(), LocalTime.MIDNIGHT)
            );
            Optional<ScreenTime> game = screenTimes.stream()
                    .filter(s -> {
                        Optional<CategoryScreenTime> gameCategoryScreenTime = s.getCategoryScreenTimes().stream()
                                .filter(c -> c.getCategory() == Category.GAME)
                                .findFirst();
                        return gameCategoryScreenTime.isPresent() && gameCategoryScreenTime.get().getDuration() > 0;
                    })
                    .findFirst();
            return game.map(screenTime -> screenTime.getUpdatedDate().isBefore(
                    LocalDateTime.of(LocalDateTime.now().toLocalDate(), LocalTime.MIDNIGHT)
            )).orElse(false);
        }
    }),

    NO_GAME_ONE_WEEK(new BadgeResolvable() {
        @Override
        public boolean resolve(User user, ScreenTimeRepository screenTimeRepository, BadgeRepository badgeRepository, UserBadgeRepository userBadgeRepository) {
            List<ScreenTime> screenTimes = screenTimeRepository.findByDate(
                    user.getId(),
                    LocalDateTime.of(LocalDateTime.now().toLocalDate(), LocalTime.MIDNIGHT)
            );
            Optional<ScreenTime> game = screenTimes.stream()
                    .filter(s -> {
                        Optional<CategoryScreenTime> gameCategoryScreenTime = s.getCategoryScreenTimes().stream()
                                .filter(c -> c.getCategory() == Category.GAME)
                                .findFirst();
                        return gameCategoryScreenTime.isPresent() && gameCategoryScreenTime.get().getDuration() > 0;
                    })
                    .findFirst();
            return game.map(screenTime -> screenTime.getUpdatedDate().isBefore(
                    LocalDateTime.of(LocalDateTime.now().minusDays(7).toLocalDate(), LocalTime.MIDNIGHT)
            )).orElse(false);
        }
    }),

    ;
//    NO_SCREEN_TIME_ONE_DAY
//    NO_SCREEN_TIME_ONE_WEEK # 스크린 타임 없는 일주일 달성
//
//    NO_GAME_ONE_DAY # 게임 없는 하루 달성
//    NO_GAME_ONE_WEEK # 게임 없는 한 주 달성
//
//    NO_SCREEN_TIME_AT_LATE_NIGHT # 늦은 시간(22시 ~ 02시)에 스크린 타임 없음 달성
//
//    INFORMATION_AND_BOOK_ONE_DAY # 독서 하루 달성
//    INFORMATION_AND_BOOK_ONE_WEEK # 독서 일주일 달성

    private final BadgeResolvable badgeResolvable;

    public boolean resolve(User user, ScreenTimeRepository screenTimeRepository, BadgeRepository badgeRepository, UserBadgeRepository userBadgeRepository) {
        return badgeResolvable.resolve(user, screenTimeRepository, badgeRepository, userBadgeRepository);
    }
}
