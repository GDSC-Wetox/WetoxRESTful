package dev.wetox.WetoxRESTful.screentime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppScreenTimeRepository extends JpaRepository<AppScreenTime, Long> {
}
