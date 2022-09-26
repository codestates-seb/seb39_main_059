package com.twentyfour_seven.catvillage.board.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.twentyfour_seven.catvillage.audit.DateTable;
import com.twentyfour_seven.catvillage.user.entity.User;
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
    private Board boardId;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    @JsonBackReference
    private User userId;

    @Column(name = "BODY", length = 500, nullable = false)
    private String body;

    @Column(name = "LIKE_COUNT", nullable = false)
    private Long likeCount;
}
