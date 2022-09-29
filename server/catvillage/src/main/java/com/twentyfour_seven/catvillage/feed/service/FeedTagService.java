package com.twentyfour_seven.catvillage.feed.service;

import com.twentyfour_seven.catvillage.feed.entity.Feed;
import com.twentyfour_seven.catvillage.feed.entity.FeedTag;
import com.twentyfour_seven.catvillage.feed.entity.TagToFeed;
import com.twentyfour_seven.catvillage.feed.repository.FeedTagRepository;
import com.twentyfour_seven.catvillage.feed.repository.TagToFeedRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FeedTagService {
    private final FeedTagRepository feedTagRepository;
    private final TagToFeedRepository tagToFeedRepository;

    public FeedTagService(FeedTagRepository feedTagRepository, TagToFeedRepository tagToFeedRepository) {
        this.feedTagRepository = feedTagRepository;
        this.tagToFeedRepository = tagToFeedRepository;
    }

    public List<FeedTag> createTags(Feed feed, List<FeedTag> feedTags) {
        feedTags.forEach(feedTag -> {
            FeedTag createFeedTag = createTag(feedTag);
            createTagToFeed(new TagToFeed(feed, createFeedTag));
        });
        return feedTags;
    }

    public FeedTag createTag(FeedTag feedTag) {
        return feedTagRepository.save(feedTag);
    }

    public TagToFeed createTagToFeed(TagToFeed tagToFeed) {
        return tagToFeedRepository.save(tagToFeed);
    }

    public List<FeedTag> findFeedTags(Feed feed) {
        List<FeedTag> feedTags = new ArrayList<>();
        feed.getTagToFeeds().forEach(tagToFeed -> feedTags.add(tagToFeed.getFeedTag()));
        return feedTags;
    }
}
