package com.twentyfour_seven.catvillage.feed.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.twentyfour_seven.catvillage.audit.DateTable;
import com.twentyfour_seven.catvillage.cat.entity.Cat;
import com.twentyfour_seven.catvillage.common.like.Like;
import com.twentyfour_seven.catvillage.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "FEED_COMMENT")
@Getter
@NoArgsConstructor
public class FeedComment extends DateTable {
    @Id
    @Column(name = "FEED_COMMENT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedCommentId;

    @Setter
    @Column(name = "BODY", length = 500, nullable = false)
    private String body;

    @Column(name = "LIKE_COUNT", nullable = false)
    private long likeCount;

    @Setter
    @ManyToOne
    @JoinColumn(name = "FEED_ID")
    @JsonBackReference
    private Feed feed;

    @Setter
    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = true)
    @JsonBackReference
    private User user;

    @Setter
    @ManyToOne
    @JoinColumn(name = "CAT_ID", nullable = true)
    @JsonBackReference
    private Cat cat;

    @OneToMany(mappedBy = "feedComment", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<Like> likes = new ArrayList<>();

    public FeedComment(String body) {
        this.body = body;
    }
}
