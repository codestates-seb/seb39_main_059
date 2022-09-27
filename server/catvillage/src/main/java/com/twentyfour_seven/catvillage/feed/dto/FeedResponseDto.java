package com.twentyfour_seven.catvillage.feed.dto;

import com.twentyfour_seven.catvillage.common.picture.dto.PictureDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedResponseDto {
    private long feedId;
    private long catId;
    private List<PictureDto> pictures;
    private String body;
    private List<FeedTagDto> tags;

    public FeedResponseDto(long feedId, long catId, List<PictureDto> pictures, String body) {
        this.feedId = feedId;
        this.catId = catId;
        this.pictures = pictures;
        this.body = body;
    }
}
