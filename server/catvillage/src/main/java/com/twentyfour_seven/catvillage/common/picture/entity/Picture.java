package com.twentyfour_seven.catvillage.common.picture.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.twentyfour_seven.catvillage.feed.entity.Feed;
import com.twentyfour_seven.catvillage.board.entity.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Entity(name = "PICTURE")
@Getter
@NoArgsConstructor
public class Picture {
    @Id
    @Column(name = "PICTURE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pictureId;

    @ManyToOne
    @JoinColumn(name = "FEED_ID")
    @JsonBackReference
    private Feed feed;

    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    @JsonBackReference
    private Board board;

    @Column(name = "PATH", length = 500)
    private String path;

    @Builder

    public Picture(Long pictureId, Feed feed, Board board, String path) {
        this.pictureId = pictureId;
        this.feed = feed;
        this.board = board;
        this.path = path;
    }
}
