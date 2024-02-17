package dev.wetox.WetoxRESTful.screentime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScreenTimeRepository extends JpaRepository<ScreenTime, Long> {
    @Query("SELECT s FROM ScreenTime s WHERE s.user.id = :userId ORDER BY s.updatedDate DESC")
    Page<ScreenTime> findLatestByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("select s FROM ScreenTime s WHERE s.user.id = :userId and s.updatedDate <= :dateDuration ORDER BY s.updatedDate DESC")
    List<ScreenTime> findByDate(@Param("userId") Long userId, LocalDateTime dateDuration);

    List<ScreenTime> findScreenTimeByUserId(Long userId);
}
