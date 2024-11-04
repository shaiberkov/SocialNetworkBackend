package org.example.socialnetworknew.controller;


import org.example.socialnetworknew.entity.Follow;
import org.example.socialnetworknew.entity.User;
import org.example.socialnetworknew.response.ApiResponse;
import org.example.socialnetworknew.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/follow")
@CrossOrigin(origins = "http://localhost:3000")
public class FollowController {

    @Autowired
    private FollowService followService;

    @PostMapping("/follow")
    public ResponseEntity<ApiResponse<String>> followUser(@RequestParam String followerUsername, @RequestParam String followingUsername) {
        ApiResponse<String> response;
        try {
            followService.followUser(followerUsername, followingUsername);
            response = new ApiResponse<>(true, "Followed successfully.", null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response = new ApiResponse<>(false, e.getMessage(), null);
            return ResponseEntity.ok(response);
        }
    }



    @PostMapping("/unfollow")
    public ResponseEntity<ApiResponse<String>> unfollowUser(@RequestParam String followerUsername, @RequestParam String followingUsername) {
        ApiResponse<String> response;
        try {
            followService.unfollowUser(followerUsername, followingUsername);
            response = new ApiResponse<>(true, "Unfollowed successfully.", null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response = new ApiResponse<>(false, e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }


    // קבלת רשימת העוקבים
//    @GetMapping("/followers/{username}")
//    public ResponseEntity<?> getFollowers(@PathVariable String username) {
//        try {
//            List<Follow> followers = followService.getFollowers(username);
//            return ResponseEntity.ok(followers);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//   }

//    @GetMapping("/followers/{username}")
//    public ResponseEntity<ApiResponse<List<User>>> getFollowers(@PathVariable String username) {
//        ApiResponse<List<User>> response;
//        try {
//            List<Follow> followers = followService.getFollowers(username);
//            List<User> followersUsers = new ArrayList<>();
//
//            // עובר על העוקבים ומוסיף את המידע של כל אחד מהם
//            for (Follow follower : followers) {
//                followersUsers.add(follower.getFollower()); // מקבל את המשתמש העוקב
//            }
//
//            response = new ApiResponse<>(true, "Followers retrieved successfully.", followersUsers);
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            response = new ApiResponse<>(false, e.getMessage());
//            return ResponseEntity.badRequest().body(response);
//        }
//    }

    @GetMapping("/followers/{username}")
    public ResponseEntity<ApiResponse<List<User>>> getFollowers(@PathVariable String username) {
        ApiResponse<List<User>> response;
        try {
            // קבלת רשימת העוקבים
            List<Follow> followers = followService.getFollowers(username);
            List<User> followerUsers = new ArrayList<>();

            // בדיקה אם אין עוקבים
            if (followers.isEmpty()) {
                response = new ApiResponse<>(false, "No followers found.");
                return ResponseEntity.ok(response);
            }

            // עובר על העוקבים ומוסיף את המידע של כל אחד מהם
            for (Follow follower : followers) {
                followerUsers.add(follower.getFollower()); // מקבל את המשתמש העוקב
            }

            response = new ApiResponse<>(true, "Followers retrieved successfully.", followerUsers);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response = new ApiResponse<>(false, e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        }
    }



    @GetMapping("/following/{username}")
    public ResponseEntity<ApiResponse<List<User>>> getFollowing(@PathVariable String username) {
        ApiResponse<List<User>> response;
        try {
            // קבלת רשימת העוקבים
            List<Follow> following = followService.getFollowing(username);
            List<User> followingUsers = new ArrayList<>();

            // בדיקה אם אין עוקבים
            if (following.isEmpty()) {
                response = new ApiResponse<>(true, "No following users found.",new ArrayList<>());
                return ResponseEntity.ok(response);
            }

            // עובר על העוקבים ומוסיף את המידע של כל אחד מהם
            for (Follow follow : following) {
                followingUsers.add(follow.getFollowing()); // מקבל את המשתמש הנעקב
            }

            response = new ApiResponse<>(true, "Following retrieved successfully.", followingUsers);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response = new ApiResponse<>(false, e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        }
    }




}
