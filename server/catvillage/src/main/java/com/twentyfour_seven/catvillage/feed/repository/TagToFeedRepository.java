package com.twentyfour_seven.catvillage.feed.repository;

import com.twentyfour_seven.catvillage.feed.entity.TagToFeed;
import com.twentyfour_seven.catvillage.feed.entity.TagToFeedId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagToFeedRepository extends JpaRepository<TagToFeed, TagToFeedId> {
}
