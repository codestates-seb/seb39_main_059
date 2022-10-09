package com.twentyfour_seven.catvillage.feed.repository;

import com.twentyfour_seven.catvillage.feed.entity.Feed;
import com.twentyfour_seven.catvillage.feed.entity.TagToFeed;
import com.twentyfour_seven.catvillage.feed.entity.TagToFeedId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagToFeedRepository extends JpaRepository<TagToFeed, TagToFeedId> {

    List<TagToFeed> findByFeed(Feed feed);
}
