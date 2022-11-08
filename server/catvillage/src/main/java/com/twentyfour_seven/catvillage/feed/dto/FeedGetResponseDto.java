package com.twentyfour_seven.catvillage.feed.dto;

import com.twentyfour_seven.catvillage.common.picture.dto.PictureDto;
import io.swagger.annotations.ApiParam;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedGetResponseDto {
    @ApiParam(value = "냥이생활 피드 식별자(id)", example = "1")
    private long feedId;

    @ApiParam(value = "피드 작성한 고양이 식별자(id)", example = "1")
    private long catId;

    @ApiParam(value = "피드 작성한 고양이 이름", example = "수라")
    private String name;

    @ApiParam(value = "피드 작성한 고양이 프로필 이미지 경로", example = "https://image.catvillage.shop.s3.ap-northeast-2.amazonaws.com/catvillage/images/9f39a517-3555-47b1-a0d4-344e73af1d0b-feed-image-1.jpg")
    private String profileImage;

    private List<PictureDto> pictures;

    @ApiParam(value = "요청을 보낸 사용자가 피드에 좋아요를 눌렀는지 여부, 좋아요를 추가했을 경우 true", example = "false")
    private Boolean isLike;

    @ApiParam(value = "요청을 보낸 사용자가 작성한 피드인지 여부, 작성한 피드인 경우 true", example = "false")
    private Boolean isMyFeed;

    @ApiParam(value = "피드에 작성한 글", example = "수라는 잠이 많아요")
    private String body;

    @ApiParam(value = "피드 좋아요 개수", example = "30")
    private long likeCount;

    private List<FeedTagDto> tags;
    private List<FeedCommentGetDto> comments;

    @Builder
    public FeedGetResponseDto(long feedId, long catId, String name, String profileImage, String body, long likeCount) {
        this.feedId = feedId;
        this.catId = catId;
        this.name = name;
        this.profileImage = profileImage;
        this.body = body;
        this.likeCount = likeCount;
    }
}

