package com.twentyfour_seven.catvillage.common.picture.dto;

import com.twentyfour_seven.catvillage.common.picture.entity.Picture;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Pattern;


@Getter
@Setter
@NoArgsConstructor
@Validated
public class PictureDto {
    @Pattern(regexp = "^((https?|http?|)://)$")
    @Length(max = 500)
    private String picture;

    public PictureDto(Picture picture) {
        this.picture = picture.getPath();
    }
}
