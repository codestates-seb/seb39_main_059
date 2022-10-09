package com.twentyfour_seven.catvillage.common.like;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.twentyfour_seven.catvillage.audit.DateTable;
import com.twentyfour_seven.catvillage.board.entity.Board;
import com.twentyfour_seven.catvillage.board.entity.BoardComment;
import com.twentyfour_seven.catvillage.feed.entity.Feed;
import com.twentyfour_seven.catvillage.feed.entity.FeedComment;
import com.twentyfour_seven.catvillage.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "LIKES")
@Getter
@NoArgsConstructor
public class Like extends DateTable {
    @Id
    @Column(name = "LIKE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @Setter
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    @JsonBackReference
    private User user;

    @Setter
    @ManyToOne
    @JoinColumn(name = "FEED_ID")
    @JsonBackReference
    private Feed feed;

    @Setter
    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    @JsonBackReference
    private Board board;

    @Setter
    @ManyToOne
    @JoinColumn(name = "FEED_COMMENT_ID")
    @JsonBackReference
    private FeedComment feedComment;

    @Setter
    @ManyToOne
    @JoinColumn(name = "BOARD_COMMENT_ID")
    @JsonBackReference
    private BoardComment boardComment;

    @Builder
    public Like(User user, Feed feed, Board board, FeedComment feedComment, BoardComment boardComment) {
        this.user = user;
        this.feed = feed;
        this.board = board;
        this.feedComment = feedComment;
        this.boardComment = boardComment;
    }
}
