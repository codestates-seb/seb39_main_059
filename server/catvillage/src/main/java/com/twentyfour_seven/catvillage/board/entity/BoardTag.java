package com.twentyfour_seven.catvillage.board.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity(name = "BOARD_TAG")
@NoArgsConstructor
@JsonIgnoreProperties(value = "tagToBoards")
public class BoardTag {
    @Setter
    @Id
    @Column(name = "BOARD_TAG_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardTagId;

    @Setter
    @Column(name = "TAG_NAME", length = 15, nullable = false, unique = true)
    private String tagName;

    @OneToMany(mappedBy = "boardTag", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private final List<TagToBoard> tagToBoards = new ArrayList<>();

    public BoardTag(String tagName) {
        this.tagName = tagName;
    }

    @Builder
    public BoardTag(Long boardTagId, String tagName) {
        this.boardTagId = boardTagId;
        this.tagName = tagName;
    }
}
