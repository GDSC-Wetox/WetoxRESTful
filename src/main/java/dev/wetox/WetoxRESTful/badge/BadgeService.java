package dev.wetox.WetoxRESTful.badge;

import dev.wetox.WetoxRESTful.exception.UserNotFoundException;
import dev.wetox.WetoxRESTful.screentime.ScreenTimeRepository;
import dev.wetox.WetoxRESTful.user.User;
import dev.wetox.WetoxRESTful.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BadgeService {
    private final UserRepository userRepository;
    private final BadgeRepository badgeRepository;
    private final UserBadgeRepository userBadgeRepository;
    private final ScreenTimeRepository screenTimeRepository;

    public BadgeListResponse listRewardedBadge(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<Badge> rewardedBadges = userBadgeRepository.findAllByUser(user).stream()
                .map(UserBadge::getBadge)
                .toList();
        List<UserBadge> userBadges = userBadgeRepository.findAllByUser(user);
        List<Badge> notRewardedBadges = badgeRepository.findAll().stream()
                .filter(badge -> !rewardedBadges.contains(badge))
                .toList();
        List<BadgeResponse> badgeList = Stream.concat(
                userBadges.stream()
                        .map(BadgeResponse::buildRewarded),
                notRewardedBadges.stream()
                        .map(BadgeResponse::buildNotRewarded)
        ).toList();
        return BadgeListResponse.builder().badgeList(badgeList).build();
    }

    @Transactional
    public BadgeListResponse updateBadge(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<Badge> rewardedBadges = userBadgeRepository.findAllByUser(user).stream()
                .map(UserBadge::getBadge)
                .toList();
        List<Badge> notRewardedBadges = badgeRepository.findAll().stream()
                .filter(badge -> !rewardedBadges.contains(badge))
                .toList();
        for (Badge badge: notRewardedBadges) {
            if (!badge.getBadgeResolver().resolve(user, screenTimeRepository, badgeRepository, userBadgeRepository)) {
                continue;
            }
            UserBadge userBadge = UserBadge.build(user, badge);
            userBadgeRepository.save(userBadge);
        }
        return listRewardedBadge(userId);
    }
}
