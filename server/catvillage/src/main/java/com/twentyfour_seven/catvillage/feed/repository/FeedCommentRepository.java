package com.twentyfour_seven.catvillage.feed.repository;

import com.twentyfour_seven.catvillage.feed.entity.FeedComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedCommentRepository extends JpaRepository<FeedComment, Long> {
}
