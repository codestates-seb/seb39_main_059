package com.twentyfour_seven.catvillage.feed.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class FeedCommentGetDto {
    private long feedCommentId;
    private long feedId;
    private Long userId;
    private Long catId;
    private String body;
    private long likeCount;
}
