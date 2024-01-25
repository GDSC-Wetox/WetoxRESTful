package dev.wetox.WetoxRESTful.friendship;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    Optional<Friendship> findByToIdAndFromId(Long toId, Long fromId);

    List<Friendship> findByToIdAndStatus(Long toId, FriendshipStatus status);

}
