package com.twentyfour_seven.catvillage.board.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.twentyfour_seven.catvillage.common.picture.dto.PictureDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BoardGetResponseDto {
    private Long boardId;
    private String title;
    private String body;
    private String name;
    private List<BoardTagDto> tags;
    private List<PictureDto> pictures;
    private Long viewCount;
    private Long likeCount;
    private Long commentCount;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;
    private Boolean isLike;
    private List<BoardCommentResponseDto> comments;

    @Builder
    public BoardGetResponseDto(Long boardId, String title, String body, String name, List<BoardTagDto> tags, List<PictureDto> pictures, Long viewCount, Long likeCount, Long commentCount, LocalDateTime createdDate, Boolean isLike, List<BoardCommentResponseDto> comments) {
        this.boardId = boardId;
        this.title = title;
        this.body = body;
        this.name = name;
        this.tags = tags;
        this.pictures = pictures;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.createdDate = createdDate;
        this.isLike = isLike;
        this.comments = comments;
    }
}
