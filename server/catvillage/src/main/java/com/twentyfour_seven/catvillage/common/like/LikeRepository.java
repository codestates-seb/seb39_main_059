package com.twentyfour_seven.catvillage.common.like;

import com.twentyfour_seven.catvillage.feed.entity.Feed;
import com.twentyfour_seven.catvillage.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {

    boolean existsByUserAndFeed(User user, Feed feed);
    void deleteByUserAndFeed(User user, Feed feed);
}
