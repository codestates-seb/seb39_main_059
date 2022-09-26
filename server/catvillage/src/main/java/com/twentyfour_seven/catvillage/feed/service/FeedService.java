package com.twentyfour_seven.catvillage.feed.service;

import com.twentyfour_seven.catvillage.feed.repository.FeedRepository;
import org.springframework.stereotype.Service;

@Service
public class FeedService {
    private final FeedRepository feedRepository;

    public FeedService(FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }
}
