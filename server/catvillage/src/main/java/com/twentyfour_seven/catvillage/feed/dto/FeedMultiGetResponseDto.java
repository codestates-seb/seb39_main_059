package com.twentyfour_seven.catvillage.feed.dto;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedMultiGetResponseDto {
    @ApiParam(value = "냥이생활 피드 식별자(id)")
    private long feedId;
    @ApiParam(value = "특정 피드의 대표이미지")
    private String image;
    @ApiParam(value = "로그인한 사용자가 좋아요를 눌렀는지 여부")
    private Boolean isLike;
    @ApiParam(value = "로그인한 사용자가 작성한 피드인지 여부")
    private Boolean isMyFeed;

    public FeedMultiGetResponseDto(long feedId, String image) {
        this.feedId = feedId;
        this.image = image;
    }
}
