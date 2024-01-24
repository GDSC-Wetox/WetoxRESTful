package dev.wetox.WetoxRESTful.friendship;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {


    @Query(" SELECT f " + "  FROM Friendship f" + " WHERE f.status = 'ACCEPT' " + "   AND f.to.id = :userId " +
            "   AND f.from.id IN (" +
            "                       SELECT f2.to.id " +
            "                         FROM Friendship f2 " +
            "                        WHERE f2.from.id = :userId " +
            "                          AND f2.status = 'ACCEPT'" +
            "                    )"
    )
    List<Friendship> findMutualAcceptedFriendships(Long userId);
    Optional<Friendship> findByToIdAndFromId(Long toId, Long fromId);

}
