package com.twentyfour_seven.catvillage.common.picture.dto;

import com.twentyfour_seven.catvillage.common.picture.entity.Picture;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PictureDto {
    private String picture;

    public PictureDto(Picture picture) {
        this.picture = picture.getPath();
    }
}
