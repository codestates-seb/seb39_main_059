package com.twentyfour_seven.catvillage.board.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BoardMultiGetResponse {
    private Long boardId;
    private String title;
    private String name;
    private String profileImage;
    private List<BoardTagDto> tags;
    private String picture;
    private Long viewCount;
    private Long likeCount;
    private Long commentCount;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    @Builder
    public BoardMultiGetResponse(Long boardId, String title, String name, String profileImage, List<BoardTagDto> tags, String picture, Long viewCount, Long likeCount, Long commentCount, LocalDateTime createdDate) {
        this.boardId = boardId;
        this.title = title;
        this.name = name;
        this.profileImage = profileImage;
        this.tags = tags;
        this.picture = picture;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.createdDate = createdDate;
    }
}
