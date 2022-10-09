package com.twentyfour_seven.catvillage.board.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity(name = "BOARD_TAG")
@NoArgsConstructor
public class BoardTag {
    @Id
    @Column(name = "BOARD_TAG_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardTagId;

    @Column(name = "TAG_NAME", length = 15, nullable = false, unique = true)
    private String tagName;

    @OneToMany(mappedBy = "boardTag", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private final List<TagToBoard> tagToBoards = new ArrayList<>();

    @Builder
    public BoardTag(Long boardTagId, String tagName) {
        this.boardTagId = boardTagId;
        this.tagName = tagName;
    }
}
