package com.twentyfour_seven.catvillage.board.dto.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BoardUserCommentResponseDto {
    private Long boardCommentId;
    private String boardTitle;
    private String body;
    private Long likeCount;
    private String link;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    @Builder
    public BoardUserCommentResponseDto(Long boardCommentId, String boardTitle, String body, Long likeCount, String link, LocalDateTime createdDate) {
        this.boardCommentId = boardCommentId;
        this.boardTitle = boardTitle;
        this.body = body;
        this.likeCount = likeCount;
        this.link = link;
        this.createdDate = createdDate;
    }
}
