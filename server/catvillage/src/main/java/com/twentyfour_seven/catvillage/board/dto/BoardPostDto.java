package com.twentyfour_seven.catvillage.board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
public class BoardPostDto {
    private String title;
    private String body;
    private ArrayList<String> tag = new ArrayList<>();
    private ArrayList<String> picture = new ArrayList<>();

    @Builder
    public BoardPostDto(String title, String body) {
        this.title = title;
        this.body = body;
    }
}
