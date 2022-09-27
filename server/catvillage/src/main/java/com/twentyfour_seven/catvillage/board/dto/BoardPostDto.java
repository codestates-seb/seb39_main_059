package com.twentyfour_seven.catvillage.board.dto;

import com.twentyfour_seven.catvillage.common.picture.dto.PictureDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BoardPostDto {
    private String title;
    private String body;
    private List<BoardTagDto> tag = new ArrayList<>();
    private List<PictureDto> picture = new ArrayList<>();

    @Builder
    public BoardPostDto(String title, String body) {
        this.title = title;
        this.body = body;
    }
}
