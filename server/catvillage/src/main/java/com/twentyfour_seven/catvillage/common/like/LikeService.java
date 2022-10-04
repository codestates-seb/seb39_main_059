package com.twentyfour_seven.catvillage.common.like;

import com.twentyfour_seven.catvillage.board.entity.Board;
import com.twentyfour_seven.catvillage.board.entity.BoardComment;
import com.twentyfour_seven.catvillage.feed.entity.Feed;
import com.twentyfour_seven.catvillage.feed.entity.FeedComment;
import com.twentyfour_seven.catvillage.user.entity.User;
import org.springframework.stereotype.Service;

@Service
public class LikeService {
    private final LikeRepository likeRepository;

    public LikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    public void addLikeInFeed(User user, Feed feed) {
        Like like = Like.builder().user(user).feed(feed).build();
        likeRepository.save(like);
    }

    public void addLikeInBoard(User user, Board board) {
        Like like = Like.builder().user(user).board(board).build();
        likeRepository.save(like);
    }

    public void addLikeInFeedComment(User user, FeedComment comment) {
        Like like = Like.builder().user(user).feedComment(comment).build();
        likeRepository.save(like);
    }

    public void addLikeInBoardComment(User user, BoardComment comment) {
        Like like = Like.builder().user(user).boardComment(comment).build();
        likeRepository.save(like);
    }

    public void deleteLikeInFeed(User user, Feed feed) {
        Like like = Like.builder().user(user).feed(feed).build();
        likeRepository.delete(like);
    }

    public void deleteLikeInBoard(User user, Board board) {
        Like like = Like.builder().user(user).board(board).build();
        likeRepository.delete(like);
    }

    public void deleteLikeInFeedComment(User user, FeedComment feedComment) {
        Like like = Like.builder().user(user).feedComment(feedComment).build();
        likeRepository.delete(like);
    }

    public void deleteLikeInBoardComment(User user, BoardComment boardComment) {
        Like like = Like.builder().user(user).boardComment(boardComment).build();
        likeRepository.delete(like);
    }
}
