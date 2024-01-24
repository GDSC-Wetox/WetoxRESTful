package dev.wetox.WetoxRESTful.badge;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BadgeDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private final BadgeRepository badgeRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        for (BadgeResolver badgeResolver: BadgeResolver.values()) {
            Badge badge = Badge.build(badgeResolver);
            badgeRepository.save(badge);
        }
    }
}
