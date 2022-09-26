package com.twentyfour_seven.catvillage.board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
public class BoardPostResponseDto {
    private Long boardId;
    private String title;
    private String body;
    private ArrayList<String> tag = new ArrayList<>();
    private ArrayList<String> picture = new ArrayList<>();

    @Builder
    public BoardPostResponseDto(Long boardId, String title, String body) {
        this.boardId = boardId;
        this.title = title;
        this.body = body;
    }
}
