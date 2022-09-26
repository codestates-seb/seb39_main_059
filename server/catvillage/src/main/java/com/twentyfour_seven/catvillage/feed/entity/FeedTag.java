package com.twentyfour_seven.catvillage.feed.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "FEED_TAG")
@Getter
@NoArgsConstructor
public class FeedTag {
    @Id
    @Column(name = "FEED_TAG_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedTagId;

    @Setter
    @Column(name = "TAG_NAME", length = 15, nullable = false)
    private String tagName;

    @Setter
    @OneToMany(mappedBy = "feedTag", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<TagToFeed> tagToFeeds = new ArrayList<>();

    public FeedTag(String tagName) {
        this.tagName = tagName;
    }
}
