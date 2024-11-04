package org.example.socialnetworknew.service;



import org.example.socialnetworknew.entity.User;
import org.example.socialnetworknew.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // יצירת משתמש חדש
    @Transactional
    public User registerUser(String username, String password) throws Exception {
        if(userRepository.existsByUsername(username)) {
            throw new Exception("Username already exists.");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password); // שמירת סיסמא בטקסט גלוי
        return userRepository.save(user);
    }

    // מציאת משתמש לפי שם משתמש
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // חיפוש משתמשים לפי חלק מהשם
    public List<User> searchUsers(String query) {
        return userRepository.findByUsernameContaining(query);
    }

    // עדכון פרופיל תמונה
    @Transactional
    public User updateProfileImage(String username, String imageUrl) throws Exception {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new Exception("User not found."));
        user.setProfileImageUrl(imageUrl);
        return userRepository.save(user);
    }

    // רשימת כל המשתמשים
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // אימות סיסמא
    public boolean authenticate(String username, String rawPassword) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if(userOpt.isPresent()) {
            return rawPassword.equals(userOpt.get().getPassword());
        }
        return false;
    }
}
