package com.twentyfour_seven.catvillage.feed.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.twentyfour_seven.catvillage.audit.DateTable;
import com.twentyfour_seven.catvillage.cat.entity.Cat;
import com.twentyfour_seven.catvillage.common.like.Like;
import com.twentyfour_seven.catvillage.common.picture.entity.Picture;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "FEED")
@Getter
@NoArgsConstructor
public class Feed extends DateTable {
    @Id
    @Column(name = "FEED_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedId;

    @Setter
    @Column(name = "BODY", length = 1000)
    private String body;

    @Setter
    @Column(name = "LIKE_COUNT", nullable = false)
    private long likeCount;

    @Setter
    @Column(name = "VIEW_COUNT", nullable = false)
    private long viewCount;

    @Setter
    @Column(name = "COMMENT_COUNT", nullable = false)
    private long commentCount;

    @Setter
    @ManyToOne
    @JoinColumn(name = "CAT_ID")
    @JsonBackReference
    private Cat cat;

    @OneToMany(mappedBy = "feed", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<TagToFeed> tagToFeeds = new ArrayList<>();

    @OneToMany(mappedBy = "feed", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<Picture> pictures = new ArrayList<>();

    @OneToMany(mappedBy = "feed", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<Like> likes = new ArrayList<>();

    public Feed(String body) {
        this.body = body;
    }

    public Feed(Long feedId) {
        this.feedId = feedId;
    }
}
