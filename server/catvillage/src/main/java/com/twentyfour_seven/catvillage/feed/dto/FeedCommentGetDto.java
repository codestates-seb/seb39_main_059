package com.twentyfour_seven.catvillage.feed.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedCommentGetDto {
    private long feedCommentId;
    private long userId;
    private long catId;
    private String body;
    private long likeCount;
}
