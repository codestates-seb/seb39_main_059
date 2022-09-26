package com.twentyfour_seven.catvillage.feed.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.twentyfour_seven.catvillage.audit.DateTable;
import com.twentyfour_seven.catvillage.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;

@Entity(name = "FEED_COMMENT")
@Getter
@NoArgsConstructor
public class FeedComment extends DateTable {
    @Id
    @Column(name = "FEED_COMMENT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedCommentId;

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
    @JoinColumn(name = "USER_ID")
    @JsonBackReference
    private User user;

    // TODO: Like 구현 후 주석 제거 필요
//    @Setter
//    @OneToMany(mappedBy = "feedCommentId")
//    @JsonManagedReference
//    private List<Like> likes = new ArrayList<>();

    public FeedComment(String body) {
        this.body = body;
    }
}
