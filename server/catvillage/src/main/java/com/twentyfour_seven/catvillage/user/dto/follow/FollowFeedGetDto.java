package com.twentyfour_seven.catvillage.user.dto.follow;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FollowFeedGetDto {
    private String profileImage;
    private long catId;
}
