package com.twentyfour_seven.catvillage.feed.service;

import com.twentyfour_seven.catvillage.feed.repository.FeedTagRepository;
import com.twentyfour_seven.catvillage.feed.repository.TagToFeedRepository;
import org.springframework.stereotype.Service;

@Service
public class FeedTagService {
    private final FeedTagRepository feedTagRepository;
    private final TagToFeedRepository tagToFeedRepository;

    public FeedTagService(FeedTagRepository feedTagRepository, TagToFeedRepository tagToFeedRepository) {
        this.feedTagRepository = feedTagRepository;
        this.tagToFeedRepository = tagToFeedRepository;
    }
}
