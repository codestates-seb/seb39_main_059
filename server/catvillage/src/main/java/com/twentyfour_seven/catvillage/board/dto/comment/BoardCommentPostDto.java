package com.twentyfour_seven.catvillage.board.dto.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
public class BoardCommentPostDto {
    private Long boardId;
    @Length(max = 500)
    private String body;

    @Builder
    public BoardCommentPostDto(Long boardId, String body) {
        this.boardId = boardId;
        this.body = body;
    }
}
