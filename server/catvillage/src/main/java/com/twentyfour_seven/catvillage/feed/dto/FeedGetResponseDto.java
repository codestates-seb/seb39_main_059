package com.twentyfour_seven.catvillage.feed.dto;

import com.twentyfour_seven.catvillage.common.picture.dto.PictureDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedGetResponseDto {
    private long feedId;
    private long catId;
    private String name;
    private String profileImage;
    private List<PictureDto> pictures;
    private String body;
    private long likeCount;
    private long commentCount;
    private List<FeedTagDto> tags;
    private List<FeedCommentGetDto> comments;

    @Builder
    public FeedGetResponseDto(long feedId, long catId, String name, String profileImage, String body, long likeCount, long commentCount) {
        this.feedId = feedId;
        this.catId = catId;
        this.name = name;
        this.profileImage = profileImage;
        this.body = body;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
    }
}

