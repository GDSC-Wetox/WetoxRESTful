package dev.wetox.WetoxRESTful.screentime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreenTimeRepository extends JpaRepository<ScreenTime, Long> {
    @Query("SELECT s FROM ScreenTime s WHERE s.user.id = :userId ORDER BY s.updatedDate DESC")
    Page<ScreenTime> findLatestByUserId(@Param("userId") Long userId, Pageable pageable);
}
