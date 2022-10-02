package com.twentyfour_seven.catvillage.board.dto.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
public class BoardCommentPatchDto {
    private Long boardCommentId;
    @Length(max = 500)
    private String body;

    @Builder
    public BoardCommentPatchDto(Long boardCommentId, String body) {
        this.boardCommentId = boardCommentId;
        this.body = body;
    }
}
