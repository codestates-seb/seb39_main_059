package com.twentyfour_seven.catvillage.board.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.twentyfour_seven.catvillage.audit.DateTable;
import com.twentyfour_seven.catvillage.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
public class Board extends DateTable {
    @Id
    @Column(name = "BOARD_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    @JsonBackReference
    @Setter
    private User userId;

    @Setter
    @Column(name = "TITLE", length = 64, nullable = false)
    private String title;

    @Setter
    @Column(name = "BODY", length = 1000, nullable = false)
    private String body;

    @Setter
    @Column(name = "LIKE_COUNT", nullable = false)
    private Long likeCount;

    @Setter
    @Column(name = "VIEW_COUNT", nullable = false)
    private Long viewCount;

    @Setter
    @Column(name = "COMMENT_COUNT", nullable = false)
    private Long commentCount;

    public Board() {
        likeCount = 0L;
        viewCount = 0L;
        commentCount = 0L;
    }

    public void setCountNull() {
        likeCount = null;
        viewCount = null;
        commentCount = null;
    }

    @Builder
    public Board(Long boardId, User userId, String title, String body) {
        this();
        this.boardId = boardId;
        this.userId = userId;
        this.title = title;
        this.body = body;
    }
}
