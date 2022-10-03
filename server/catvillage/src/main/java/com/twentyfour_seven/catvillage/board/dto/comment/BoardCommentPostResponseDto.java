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
public class BoardCommentPostResponseDto {
    private Long boardCommentId;
    private Long boardId;
    private Long userId;
    private String body;
    private Long likeCount;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedDate;

    @Builder
    public BoardCommentPostResponseDto(Long boardCommentId, Long boardId, Long userId, String body, Long likeCount, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.boardCommentId = boardCommentId;
        this.boardId = boardId;
        this.userId = userId;
        this.body = body;
        this.likeCount = likeCount;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
