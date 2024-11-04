package org.example.socialnetworknew.entity;



import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username")
})
public class User {

    @Id
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable=false)
    private String password;

    @Column
    private String profileImageUrl;



    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
//    private Set<Post> posts = new HashSet<>();
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Follow> following = new HashSet<>();

    @OneToMany(mappedBy = "following", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Follow> followers = new HashSet<>();

    // getters and setters
    // ניתן להשתמש ב-Lombok ליצירת getters/setters אוטומטית
}
