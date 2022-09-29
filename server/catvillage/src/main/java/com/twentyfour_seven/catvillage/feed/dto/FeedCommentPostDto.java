package com.twentyfour_seven.catvillage.feed.dto;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class FeedCommentPostDto {
    @Positive
    private long profileId;

    @ApiParam(value = "댓글 작성자가 cat인지 여부(cat일 경우 true, user일 경우 false)")
    private boolean isCat;

    @ApiParam(value = "글자 수 제한 500자")
    @Length(max = 500)
    private String body;
}
