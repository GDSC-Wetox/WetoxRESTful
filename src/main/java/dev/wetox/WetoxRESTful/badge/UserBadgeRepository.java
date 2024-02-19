package dev.wetox.WetoxRESTful.badge;

import dev.wetox.WetoxRESTful.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserBadgeRepository extends JpaRepository<UserBadge, Long> {
    @Query("SELECT ub FROM UserBadge ub WHERE ub.user = :user")
    List<UserBadge> findAllByUser(@Param("user") User user);
}
