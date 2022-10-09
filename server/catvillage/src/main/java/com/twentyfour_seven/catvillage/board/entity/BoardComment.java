package com.twentyfour_seven.catvillage.board.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.twentyfour_seven.catvillage.audit.DateTable;
import com.twentyfour_seven.catvillage.common.like.Like;
import com.twentyfour_seven.catvillage.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity(name = "BOARD_COMMENT")
public class BoardComment extends DateTable {
    @Id
    @Column(name = "BOARD_COMMENT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardCommentId;

    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    @JsonBackReference
    @Setter
    private Board board;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    @JsonBackReference
    @Setter
    private User user;

    @Column(name = "BODY", length = 500, nullable = false)
    @Setter
    private String body;

    @Column(name = "LIKE_COUNT", nullable = false)
    @Setter
    private Long likeCount;

    @OneToMany(mappedBy = "boardComment", cascade = CascadeType.REMOVE)
    private List<Like> likes = new ArrayList<>();

    public BoardComment() {
        this.likeCount = 0L;
    }

    @Builder
    public BoardComment(Long boardCommentId, Board board, User user, String body) {
        this();
        this.boardCommentId = boardCommentId;
        this.board = board;
        this.user = user;
        this.body = body;
    }
}
