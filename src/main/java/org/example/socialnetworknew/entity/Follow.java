package org.example.socialnetworknew.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "follows", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"follower_username", "following_username"})
})
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // המשתמש שעוקב
//    @ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "follower_username", nullable=false)
    @JsonBackReference
//    @JsonIgnore
    private User follower;

    // המשתמש שנעקף
//    @ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "following_username", nullable=false)
    @JsonBackReference
//    @JsonIgnore
    private User following;

    // getters and setters
}
