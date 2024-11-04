package org.example.socialnetworknew.service;



import org.example.socialnetworknew.entity.Post;
import org.example.socialnetworknew.entity.User;
import org.example.socialnetworknew.entity.Follow;
import org.example.socialnetworknew.repository.PostRepository;
import org.example.socialnetworknew.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;
    //TODO
    // יצירת פוסט חדש
    @Transactional
    public Post createPost(String username, String content) throws Exception {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new Exception("User not found."));
        Post post = new Post();
        post.setContent(content);
        post.setUser(user);
        return postRepository.save(post);
    }

//    // קבלת כל הפוסטים של משתמש
    public List<Post> getPostsByUser(String username) throws Exception {
        if(!userRepository.existsByUsername(username)) {
            throw new Exception("User not found.");
        }
        return postRepository.findByUserUsername(username);
    }
    // קבלת כל הפוסטים של העוקבים
    public List<Post> getFeed(String username, UserService userService, FollowService followService) throws Exception {
        List<Follow> following = followService.getFollowing(username);
        List<Post> feed = following.stream()
                .flatMap(follow -> postRepository.findByUserUsername(follow.getFollowing().getUsername()).stream())
                .toList();
        return feed;
    }
}
