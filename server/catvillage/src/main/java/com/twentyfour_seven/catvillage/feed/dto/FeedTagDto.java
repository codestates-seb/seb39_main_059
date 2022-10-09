package com.twentyfour_seven.catvillage.feed.dto;

import com.twentyfour_seven.catvillage.feed.entity.FeedTag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@NoArgsConstructor
@Validated
public class FeedTagDto {
    @Length(max = 15)
    private String tag;

    public FeedTagDto (FeedTag feedTag) {
        this.tag = feedTag.getTagName();
    }
}
