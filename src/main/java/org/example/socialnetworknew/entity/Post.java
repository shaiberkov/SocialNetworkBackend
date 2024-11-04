package org.example.socialnetworknew.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=1000)
    private String content;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="username", nullable=false)
    @JsonBackReference
    private User user;


    // getters and setters
}
