package com.twentyfour_seven.catvillage.feed.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedSimpleDto {
    private Long feedId;
    private String picture;
}
