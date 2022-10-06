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
public class BoardCommentResponseDto {
    private Long boardCommentId;
    private String body;
    private String name;
    private String profileImage;
    private String link;
    private Long likeCount;
    // 생성일을 사용하지 않아 주석처리
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime createdDate;

    @Builder
    public BoardCommentResponseDto(Long boardCommentId, String body, String name, String profileImage, String link, Long likeCount) {
        this.boardCommentId = boardCommentId;
        this.body = body;
        this.name = name;
        this.profileImage = profileImage;
        this.link = link;
        this.likeCount = likeCount;
    }
}
