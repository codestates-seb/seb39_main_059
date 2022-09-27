package com.twentyfour_seven.catvillage.feed.dto;

import com.twentyfour_seven.catvillage.feed.entity.FeedTag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FeedTagDto {
    private String tag;

    public FeedTagDto (FeedTag feedTag) {
        this.tag = feedTag.getTagName();
    }
}
