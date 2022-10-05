package com.twentyfour_seven.catvillage.board.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.twentyfour_seven.catvillage.audit.DateTable;
import com.twentyfour_seven.catvillage.common.like.Like;
import com.twentyfour_seven.catvillage.common.picture.entity.Picture;
import com.twentyfour_seven.catvillage.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "BOARD")
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
    private User user;

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

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private final List<Picture> pictures = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private final List<BoardComment> boardComments = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private final List<TagToBoard> tagToBoards = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private final List<Like> likes = new ArrayList<>();

    // TODO: Save 구현 후 주석 제거 필요
//    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
//    @JsonManagedReference
//    private final List<Save> saves = new ArrayList<>();

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
    public Board(Long boardId, User user, String title, String body) {
        this();
        this.boardId = boardId;
        this.user = user;
        this.title = title;
        this.body = body;
    }
}
