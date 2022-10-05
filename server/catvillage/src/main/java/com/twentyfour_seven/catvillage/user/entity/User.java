package com.twentyfour_seven.catvillage.user.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.twentyfour_seven.catvillage.audit.DateTable;
import com.twentyfour_seven.catvillage.board.entity.Board;
import com.twentyfour_seven.catvillage.board.entity.BoardComment;
import com.twentyfour_seven.catvillage.cat.entity.Cat;
import com.twentyfour_seven.catvillage.common.like.Like;
import com.twentyfour_seven.catvillage.feed.entity.FeedComment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "USERS")
@Getter
public class User extends DateTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "EMAIL", nullable = false, unique = true, length = 50)
    private String email;

    @Setter
    @Column(name = "PASSWORD", length = 120)
    @JsonIgnore
    private String password;

    @Setter
    @Column(name = "NAME", nullable = false, unique = true, length = 16)
    private String name;

    @Setter
    @Column(name = "PROFILE_IMAGE", length = 500)
    private String profileImage;

    @Setter
    @Column(name = "LOCATION", length = 30)
    private String location;

    @Setter
    @Column(name = "CONTENT_COUNT")
    private Long contentCount;

    @Setter
    @Column(name = "FOLLOWING_COUNT")
    private Long followingCount;

    @Setter
    @Column(name = "FOLLOWER_COUNT")
    private Long followerCount;

    @Setter
    @Column(name = "ROLE", length = 15)
    private String role;

    @Setter
    @Column(name = "EXPIRY_DATE")
    private LocalDateTime expiryDate;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private final List<Follow> following = new ArrayList<>();

    @OneToMany(mappedBy = "target", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private final List<Follow> follower = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private final List<Cat> cats = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private final List<FeedComment> feedComments = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private final List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private final List<BoardComment> boardComments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private final List<Like> likes = new ArrayList<>();

    public User() {
        contentCount = 0L;
        followerCount = 0L;
        followingCount = 0L;
        expiryDate = null;
        role = "ROLE_USER";
    }

    @Builder
    public User(Long userId, String email, String password, String name, String profileImage, String location) {
        this();
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.profileImage = profileImage;
        this.location = location;
    }

    public User(Long userId) {
        this.userId = userId;
    }
}
