package com.twentyfour_seven.catvillage.common.like;

import com.twentyfour_seven.catvillage.board.entity.Board;
import com.twentyfour_seven.catvillage.board.entity.BoardComment;
import com.twentyfour_seven.catvillage.feed.entity.Feed;
import com.twentyfour_seven.catvillage.feed.entity.FeedComment;
import com.twentyfour_seven.catvillage.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {

    boolean existsByUserAndFeed(User user, Feed feed);

    void deleteByUserAndFeed(User user, Feed feed);

    boolean existsByUserAndBoard(User user, Board board);

    void deleteByUserAndBoard(User user, Board board);

    boolean existsByUserAndFeedComment(User user, FeedComment feedComment);

    void deleteByUserAndFeedComment(User user, FeedComment feedComment);

    boolean existsByUserAndBoardComment(User user, BoardComment boardComment);

    void deleteByUserAndBoardComment(User user, BoardComment boardComment);

    List<Like> findByUserAndFeedIsNotNull(User user);
}
