package dev.wetox.WetoxRESTful.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNickname(String nickname);

    Optional<User> findByOauthProviderAndOauthSubject(OAuthProvider oAuthProvider, String subject);

    @Query("SELECT u FROM User u WHERE u.nickname LIKE %:nickname%")
    List<User> findByNicknameContain(@Param("nickname") String nickname);

}
