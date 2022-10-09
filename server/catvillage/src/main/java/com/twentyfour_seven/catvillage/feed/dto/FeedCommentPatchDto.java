package com.twentyfour_seven.catvillage.feed.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class FeedCommentPatchDto {
    @Length(max = 500)
    private String body;
}
