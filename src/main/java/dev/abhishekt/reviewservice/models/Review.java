package dev.abhishekt.reviewservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Review extends BaseModel{
    private String comment;
    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;
    @ManyToOne
    private User user;
    @ManyToOne
    private Service service;
    @ManyToOne
    private Rating rating;
    @ManyToMany
    @JoinTable(
            name = "review_likes",
            joinColumns = @JoinColumn(name = "review_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> likes = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "review_dislikes",
            joinColumns = @JoinColumn(name = "review_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> dislikes = new HashSet<>();
    private int getLikeCount(){
        return likes.size();
    }
    private int getDislikeCount(){
        return dislikes.size();
    }
}
