package com.twentyfour_seven.catvillage.board.dto;

import com.twentyfour_seven.catvillage.common.picture.dto.PictureDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BoardPostDto {
    @Length(max = 64)
    private String title;
    @Length(max = 1000)
    private String body;
    private List<BoardTagDto> tag;
    private List<PictureDto> picture;

    @Builder
    public BoardPostDto(String title, String body, List<BoardTagDto> tag, List<PictureDto> picture) {
        this.title = title;
        this.body = body;
        this.tag = tag;
        this.picture = picture;
    }
}
