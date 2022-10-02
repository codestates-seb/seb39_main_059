package com.twentyfour_seven.catvillage.feed.repository;

import com.twentyfour_seven.catvillage.feed.entity.FeedTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeedTagRepository extends JpaRepository<FeedTag, Long> {
    Optional<FeedTag> findByTagName(String tagName);
}
