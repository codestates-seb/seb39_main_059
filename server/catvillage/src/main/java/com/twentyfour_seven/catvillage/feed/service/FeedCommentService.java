package com.twentyfour_seven.catvillage.feed.service;

import com.twentyfour_seven.catvillage.feed.repository.FeedCommentRepository;
import org.springframework.stereotype.Service;

@Service
public class FeedCommentService {
    private final FeedCommentRepository feedCommentRepository;

    public FeedCommentService(FeedCommentRepository feedCommentRepository) {
        this.feedCommentRepository = feedCommentRepository;
    }
}
