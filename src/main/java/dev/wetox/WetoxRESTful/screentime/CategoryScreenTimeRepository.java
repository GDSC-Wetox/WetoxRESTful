package dev.wetox.WetoxRESTful.screentime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryScreenTimeRepository extends JpaRepository<CategoryScreenTime, Long> {
}
