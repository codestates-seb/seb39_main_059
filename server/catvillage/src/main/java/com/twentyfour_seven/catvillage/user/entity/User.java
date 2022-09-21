package com.twentyfour_seven.catvillage.user.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.twentyfour_seven.catvillage.audit.DateTable;
import com.twentyfour_seven.catvillage.cat.entity.Cat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
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
    @Column(name = "PASSWORD", nullable = false, length = 25)
    @JsonIgnore
    private String password;

    @Setter
    @Column(name = "NAME", nullable = false, unique = true, length = 16)
    private String name;

    @Setter
    @Column(name = "PROFILE_IMAGE", length = 100)
    private String profileImage;

    @Setter
    @Column(name = "LOCATION", length = 30)
    private String location;

    @Setter
    @Column(name = "CAT_COUNT")
    private long catCount;

    @Setter
    @Column(name = "CONTENT_COUNT")
    private long contentCount;

    @Setter
    @Column(name = "FOLLOWING_COUNT")
    private long followingCount;

    @Setter
    @Column(name = "FOLLOWER_COUNT")
    private long followerCount;

    @Setter
    @Column(name = "ROLE", length = 15)
    private String role;

    @Setter
    @Column(name = "EXPIRY_DATE")
    private Date expiryDate;

    @JsonManagedReference
    @OneToMany(mappedBy = "memberId", cascade = CascadeType.REMOVE)
    private final List<Follow> following = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "targetId", cascade = CascadeType.REMOVE)
    private final List<Follow> follower = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private final List<Cat> cats = new ArrayList<>();

    public User() {
        catCount = 0;
        contentCount = 0;
        followerCount = 0;
        followingCount = 0;
        expiryDate = null;
    }

    @Builder
    public User(Long userId, String email, String password, String name, String profileImage, String location, String role) {
        this();
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.profileImage = profileImage;
        this.location = location;
        this.role = role;
    }
}
