package com.twentyfour_seven.catvillage.feed.service;

import com.twentyfour_seven.catvillage.feed.entity.Feed;
import com.twentyfour_seven.catvillage.feed.entity.FeedComment;
import com.twentyfour_seven.catvillage.feed.repository.FeedCommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedCommentService {
    private final FeedCommentRepository feedCommentRepository;

    public FeedCommentService(FeedCommentRepository feedCommentRepository) {
        this.feedCommentRepository = feedCommentRepository;
    }

    public List<FeedComment> findComments(Feed feed) {
        List<FeedComment> feedComments = feedCommentRepository.findByFeed(feed);
        return feedComments;
    }
}
