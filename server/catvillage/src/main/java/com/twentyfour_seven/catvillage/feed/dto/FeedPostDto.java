package com.twentyfour_seven.catvillage.feed.dto;

import com.twentyfour_seven.catvillage.common.picture.dto.PictureDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Validated
public class FeedPostDto {
    @Positive
    private long catId;
    @NotEmpty
    @Valid
    private List<PictureDto> pictures;
    private String body;
    private List<FeedTagDto> tags;
}
