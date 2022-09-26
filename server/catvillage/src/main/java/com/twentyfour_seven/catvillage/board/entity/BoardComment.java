package com.twentyfour_seven.catvillage.board.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.twentyfour_seven.catvillage.audit.DateTable;
import com.twentyfour_seven.catvillage.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity(name = "BOARD_COMMENT")
public class BoardComment extends DateTable {
    @Id
    @Column(name = "BOARD_COMMENT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardCommentId;

    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    @JsonBackReference
    private Board board;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    @JsonBackReference
    private User user;

    @Column(name = "BODY", length = 500, nullable = false)
    private String body;

    @Column(name = "LIKE_COUNT", nullable = false)
    private Long likeCount;

    @Builder
    public BoardComment(Long boardCommentId, Board board, User user, String body, Long likeCount) {
        this.boardCommentId = boardCommentId;
        this.board = board;
        this.user = user;
        this.body = body;
        this.likeCount = likeCount;
    }
}
