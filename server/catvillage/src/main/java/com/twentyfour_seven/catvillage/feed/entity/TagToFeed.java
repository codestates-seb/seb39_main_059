package com.twentyfour_seven.catvillage.feed.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "TAG_TO_FEED")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(TagToFeedId.class)
public class TagToFeed {
    @Id
    @ManyToOne
    @JoinColumn(name = "FEED_ID")
    @JsonBackReference
    private Feed feed;

    @Id
    @ManyToOne
    @JoinColumn(name = "FEED_TAG_ID")
    @JsonBackReference
    private FeedTag feedTag;
}
