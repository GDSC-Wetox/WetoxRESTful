package dev.wetox.WetoxRESTful.badge;

import dev.wetox.WetoxRESTful.exception.MemberNotFoundException;
import dev.wetox.WetoxRESTful.user.User;
import dev.wetox.WetoxRESTful.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BadgeService {
    private final UserRepository userRepository;
    private final BadgeRepository badgeRepository;
    private final UserBadgeRepository userBadgeRepository;

    public List<UserBadgeResponse> listRewardedBadge(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(MemberNotFoundException::new);
        List<UserBadge> userBadges = userBadgeRepository.findAllByUser(user);
        return userBadges.stream().map(UserBadgeResponse::new).toList();
    }

    @Transactional
    public List<UserBadgeResponse> updateBadge(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(MemberNotFoundException::new);
        List<Badge> rewardedBadges = userBadgeRepository.findAllByUser(user).stream()
                .map(UserBadge::getBadge)
                .toList();
        List<Badge> notRewardedBadges = badgeRepository.findAll().stream()
                .filter(badge -> !rewardedBadges.contains(badge))
                .toList();
        for (Badge badge: notRewardedBadges) {
            if (!badge.getBadgeResolver().resolve(user)) {
                continue;
            }
            UserBadge userBadge = UserBadge.build(user, badge);
            userBadgeRepository.save(userBadge);
        }
        return listRewardedBadge(userId);
    }
}
