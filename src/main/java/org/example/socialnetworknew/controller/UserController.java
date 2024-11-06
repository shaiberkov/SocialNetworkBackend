package org.example.socialnetworknew.controller;
import org.example.socialnetworknew.entity.User;
import org.example.socialnetworknew.response.ApiResponse;
import org.example.socialnetworknew.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> registerUser(@RequestParam String username, @RequestParam String password) {
        try {
            userService.registerUser(username, password);
            ApiResponse<Void> response = new ApiResponse<>(true, "User registered successfully.", null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<Void> response = new ApiResponse<>(false, "user already have an account", "REGISTRATION_ERROR");
            return ResponseEntity.ok(response);
        }
    }


    // התחברות
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Void>> loginUser(@RequestParam String username, @RequestParam String password) {
        boolean authenticated = userService.authenticate(username, password);
        if (authenticated) {
            ApiResponse<Void> response = new ApiResponse<>(true, "Login successful.");
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<Void> response = new ApiResponse<>(false, "Invalid username or password.", "LOGIN_ERROR");
            return ResponseEntity.ok(response);
        }
    }


    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<User>>> searchUsers(@RequestParam String query) {

        List<User> users = userService.searchUsers(query);

        if (users.isEmpty()) {
            ApiResponse<List<User>> response = new ApiResponse<>(false, "No users found.", null);
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<List<User>> response = new ApiResponse<>(true, "Users found.", users);
            return ResponseEntity.ok(response);
        }

    }

    @GetMapping("/user")
    public ResponseEntity<ApiResponse<User>> getUser(@RequestParam String username) {
        ApiResponse<User> response;
        try {
            Optional<User> user = userService.findByUsername(username);
            if (user.isPresent()) {
                response = new ApiResponse<>(true, "User found successfully.", user.get());
                return ResponseEntity.ok(response);
            } else {
                response = new ApiResponse<>(false, "User not found.");
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            response = new ApiResponse<>(false, e.getMessage());
            return ResponseEntity.ok(response);
        }
    }


    //    // עדכון תמונת פרופיל
    @PutMapping("/{username}/profile-image")
    public ResponseEntity<ApiResponse<Void>> updateProfileImage(@PathVariable String username, @RequestParam String imageUrl) {
        try {
            userService.updateProfileImage(username, imageUrl);
            ApiResponse<Void> response = new ApiResponse<>(true, "Profile image updated successfully",null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<Void> response = new ApiResponse<>(false, "Failed to update profile image:","The provided image URL is invalid.");
            return ResponseEntity.ok(response);
        }
    }
    //TODO
//    @PutMapping("/{username}/profile-image")
//    public ResponseEntity<ApiResponse<Void>> updateProfileImage(@PathVariable String username, @RequestParam String imageUrl) {
//        try {
//            // בדיקת תקינות הקישור
//            if (imageUrl == null || (!isImageUrl(imageUrl) && !isBase64Image(imageUrl))) {
//                ApiResponse<Void> response = new ApiResponse<>(false, "Failed to update profile image", "The provided image URL is invalid.");
//                return ResponseEntity.badRequest().body(response);
//            }
//            userService.updateProfileImage(username, imageUrl);
//            ApiResponse<Void> response = new ApiResponse<>(true, "Profile image updated successfully");
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            ApiResponse<Void> response = new ApiResponse<>(false, "Failed to update profile image", e.getMessage());
//            return ResponseEntity.badRequest().body(response);
//        }
//    }


    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        ApiResponse<List<User>> apiResponse;
        if (users != null && !users.isEmpty()) {
            apiResponse = new ApiResponse<>(true, users);
        } else {
            apiResponse = new ApiResponse<>(false, "no users found", "NO_USERS_FOUND");
        }
        return ResponseEntity.ok(apiResponse);
    }


//    private boolean isImageUrl(String url) {
//        try {
//            URL urlObj = new URL(url);
//            HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
//            connection.setRequestMethod("HEAD"); // Use HEAD request to get headers only
//            connection.connect();
//
//            // Get the Content-Type from the response header
//            String contentType = connection.getContentType();
//
//            // Check if the content type is an image
//            return contentType != null && contentType.startsWith("image/");
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }



    private boolean isValidImageUrl(String url) {
        if (url == null) {
            return false;
        }
        // בדיקה אם ה-URL הוא data:image/ עם base64
        if (url.startsWith("data:image/") && url.contains("base64,")) {
            // ניתן להוסיף בדיקות נוספות על החלק Base64 אם נדרש
            return true;
        }
        try {
            URL urlObj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
            connection.setRequestMethod("HEAD"); // Use HEAD request to get headers only
            connection.connect();

            // Get the Content-Type from the response header
            String contentType = connection.getContentType();

            // Check if the content type is an image
            return contentType != null && contentType.startsWith("image/");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }





}

