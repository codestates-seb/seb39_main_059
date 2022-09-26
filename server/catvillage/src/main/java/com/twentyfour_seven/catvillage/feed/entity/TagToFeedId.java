package com.twentyfour_seven.catvillage.feed.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagToFeedId implements Serializable {
    private Feed feed;
    private FeedTag feedTag;
}
