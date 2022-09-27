package com.twentyfour_seven.catvillage.board.dto;

import com.twentyfour_seven.catvillage.board.entity.BoardTag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardTagDto {
    private String tag;

    public BoardTagDto(BoardTag boardTag) {
        this.tag = boardTag.getTagName();
    }
}
