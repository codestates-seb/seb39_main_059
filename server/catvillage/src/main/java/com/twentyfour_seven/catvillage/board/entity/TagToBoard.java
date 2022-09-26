package com.twentyfour_seven.catvillage.board.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity(name = "TAG_TO_BOARD")
@NoArgsConstructor
@IdClass(TagToBoardId.class)
public class TagToBoard {
    @Id
    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    @JsonBackReference
    private Board board;

    @Id
    @ManyToOne
    @JoinColumn(name = "BOARD_TAG_ID")
    @JsonBackReference
    private BoardTag boardTag;

    @Builder
    public TagToBoard(Board board, BoardTag boardTag) {
        this.board = board;
        this.boardTag = boardTag;
    }
}
