package com.twentyfour_seven.catvillage.common.picture.dto;

import com.twentyfour_seven.catvillage.common.picture.entity.Picture;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;

@Getter
@Setter
@NoArgsConstructor
public class PictureDto {
    @Length(max = 500)
    private String picture;

    public PictureDto(Picture picture) {
        this.picture = picture.getPath();
    }
}
