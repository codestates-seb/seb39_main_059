package com.twentyfour_seven.catvillage.feed.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedResponseDto {
    private long feedId;
    private long catId;
    private List<String> picture;
    private String body;
    private List<String> tag;
}
