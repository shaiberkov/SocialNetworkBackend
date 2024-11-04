package org.example.socialnetworknew.service;

import org.example.socialnetworknew.entity.Follow;
import org.example.socialnetworknew.entity.User;
import org.example.socialnetworknew.repository.FollowRepository;
import org.example.socialnetworknew.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FollowService {

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserRepository userRepository;

    // לעקוב אחרי משתמש
    @Transactional
    public void followUser(String followerUsername, String followingUsername) throws Exception {
        if(followerUsername.equals(followingUsername)) {
            throw new Exception("Cannot follow yourself.");
        }
        User follower = userRepository.findByUsername(followerUsername)
                .orElseThrow(() -> new Exception("Follower not found."));
        User following = userRepository.findByUsername(followingUsername)
                .orElseThrow(() -> new Exception("User to follow not found."));
        if(followRepository.findByFollowerUsernameAndFollowingUsername(followerUsername, followingUsername).isPresent()) {
            throw new Exception("Already following this user.");
        }
        Follow follow = new Follow();
        follow.setFollower(follower);
        follow.setFollowing(following);
        followRepository.save(follow);
    }

    // להפסיק לעקוב אחרי משתמש
    @Transactional
    public void unfollowUser(String followerUsername, String followingUsername) throws Exception {
        Follow follow = followRepository.findByFollowerUsernameAndFollowingUsername(followerUsername, followingUsername)
                .orElseThrow(() -> new Exception("Follow relationship does not exist."));
        followRepository.delete(follow);
    }

    // קבלת רשימת העוקבים
    public List<Follow> getFollowers(String username) throws Exception {
        if(!userRepository.existsByUsername(username)) {
            throw new Exception("User not found.");
        }
        return followRepository.findByFollowingUsername(username);
    }

    // קבלת רשימת העוקפים
    public List<Follow> getFollowing(String username) throws Exception {
        if(!userRepository.existsByUsername(username)) {
            throw new Exception("User not found.");
        }
        return followRepository.findByFollowerUsername(username);
    }
}
