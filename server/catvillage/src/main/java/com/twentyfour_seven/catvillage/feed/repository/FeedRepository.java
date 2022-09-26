package com.twentyfour_seven.catvillage.feed.repository;

import com.twentyfour_seven.catvillage.feed.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedRepository extends JpaRepository<Feed, Long> {
}
