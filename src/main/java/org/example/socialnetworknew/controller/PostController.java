package org.example.socialnetworknew.controller;



import org.example.socialnetworknew.entity.Post;
import org.example.socialnetworknew.response.ApiResponse;
import org.example.socialnetworknew.service.PostService;
import org.example.socialnetworknew.service.UserService;
import org.example.socialnetworknew.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "http://localhost:3000")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private FollowService followService;


@PostMapping("/create")
public ResponseEntity<ApiResponse<Post>> createPost(@RequestParam String username, @RequestParam String content) throws Exception {
        ApiResponse<Post> response;
        Post post = postService.createPost(username, content);
        response = new ApiResponse<>(true, "Post created successfully.", post);
        return ResponseEntity.ok(response);

}

    // קבלת פוסטים של משתמש
    @GetMapping("/user/{username}")
    public ResponseEntity<ApiResponse<List<Post>>> getPostsByUser(@PathVariable String username) {
        ApiResponse<List<Post>> response;
        try {
            List<Post> posts = postService.getPostsByUser(username);
            response = new ApiResponse<>(true, "Posts retrieved successfully.", posts);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response = new ApiResponse<>(false, "Error retrieving posts: " + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/feed/{username}")
    public ResponseEntity<ApiResponse<List<Post>>> getFeed(@PathVariable String username) {
        ApiResponse<List<Post>> response;
        try {
            List<Post> feed = postService.getFeed(username, userService, followService);
            response = new ApiResponse<>(true, "Feed retrieved successfully.", feed);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response = new ApiResponse<>(false, "Error retrieving feed: " + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

}
