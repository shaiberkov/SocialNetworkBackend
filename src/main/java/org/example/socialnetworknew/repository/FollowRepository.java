package org.example.socialnetworknew.repository;



import org.example.socialnetworknew.entity.Follow;
import org.example.socialnetworknew.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findByFollowerUsername(String followerUsername);
    List<Follow> findByFollowingUsername(String followingUsername);
    Optional<Follow> findByFollowerUsernameAndFollowingUsername(String followerUsername, String followingUsername);
}
