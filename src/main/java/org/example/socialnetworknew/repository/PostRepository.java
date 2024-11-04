package org.example.socialnetworknew.repository;



import org.example.socialnetworknew.entity.Post;
import org.example.socialnetworknew.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserUsername(String username);
}
