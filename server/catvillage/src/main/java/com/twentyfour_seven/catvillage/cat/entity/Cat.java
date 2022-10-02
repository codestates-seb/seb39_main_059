package com.twentyfour_seven.catvillage.cat.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.twentyfour_seven.catvillage.feed.entity.Feed;
import com.twentyfour_seven.catvillage.feed.entity.FeedComment;
import com.twentyfour_seven.catvillage.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "CAT")
@Getter
@NoArgsConstructor
public class Cat {
    @Id
    @Column(name = "CAT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long catId;

    @Setter
    @Column(name = "NAME", length = 16, nullable = false)
    private String name;

    @Setter
    @Column(name = "BIRTH_DATE")
    private LocalDateTime birthDate;

    @Setter
    @Column(name = "SEX", length = 1)
    private String sex;

    @Setter
    @Column(name = "WEIGHT")
    private int weight;

    @Setter
    @Column(name = "IMAGE", length = 500)
    private String image;

    @Setter
    @Column(name = "BODY", length = 1000)
    private String body;

    @Setter
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "cat", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private final List<Feed> feeds = new ArrayList<>();

    @OneToMany(mappedBy = "cat", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private final List<FeedComment> feedComments = new ArrayList<>();

    // TODO: CatInfo 구현 후 주석 제거 필요
//    @Setter
//    @ManyToOne
//    @JoinColumn(name = "BREED_ID")
//    @JsonBackReference
//    private CatInfo breed;

    @OneToMany(mappedBy = "cat", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<TagToCat> tagToCats = new ArrayList<>();

    @Builder
    public Cat(String name, LocalDateTime birthDate, String sex, int weight, String body, String image) {
        this.name = name;
        this.birthDate = birthDate;
        this.sex = sex;
        this.weight = weight;
        this.body = body;
        this.image = image;
    }
}
