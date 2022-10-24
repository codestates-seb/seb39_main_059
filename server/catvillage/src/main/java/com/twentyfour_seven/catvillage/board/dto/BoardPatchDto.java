package com.twentyfour_seven.catvillage.board.dto;

import com.twentyfour_seven.catvillage.common.picture.dto.PictureDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Validated
public class BoardPatchDto {
    private Long boardId;
    @Length(max = 64)
    private String title;
    @Length(max = 1000)
    private String body;

    @NotEmpty
    private List<BoardTagDto> tags = new ArrayList<>();

    @Valid
    private List<PictureDto> pictures = new ArrayList<>();

    @Builder
    public BoardPatchDto(Long boardId, String title, String body, List<BoardTagDto> tags, List<PictureDto> pictures) {
        this.boardId = boardId;
        this.title = title;
        this.body = body;
        this.tags = tags;
        this.pictures = pictures;
    }
}
