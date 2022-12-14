package com.twentyfour_seven.catvillage.feed.service;

import com.twentyfour_seven.catvillage.feed.entity.Feed;
import com.twentyfour_seven.catvillage.feed.entity.FeedTag;
import com.twentyfour_seven.catvillage.feed.entity.TagToFeed;
import com.twentyfour_seven.catvillage.feed.entity.TagToFeedId;
import com.twentyfour_seven.catvillage.feed.repository.FeedTagRepository;
import com.twentyfour_seven.catvillage.feed.repository.TagToFeedRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FeedTagService {
    private final FeedTagRepository feedTagRepository;
    private final TagToFeedRepository tagToFeedRepository;

    public FeedTagService(FeedTagRepository feedTagRepository, TagToFeedRepository tagToFeedRepository) {
        this.feedTagRepository = feedTagRepository;
        this.tagToFeedRepository = tagToFeedRepository;
    }

    public List<FeedTag> createTags(Feed feed, List<FeedTag> feedTags) {
        if (feedTags.isEmpty()) {
            return null;
        }
        List<FeedTag> saveFeedTags = new ArrayList<>();
        feedTags.forEach(feedTag -> {
            FeedTag createFeedTag = createTag(feedTag);
            createTagToFeed(new TagToFeed(feed, createFeedTag));
            saveFeedTags.add(createFeedTag);
        });
        return saveFeedTags;
    }

    public FeedTag createTag(FeedTag feedTag) {
        Optional<FeedTag> optionalFeedTag = feedTagRepository.findByTagName(feedTag.getTagName());
        return optionalFeedTag.orElse(feedTagRepository.save(feedTag));
    }

    public TagToFeed createTagToFeed(TagToFeed tagToFeed) {
        return tagToFeedRepository.save(tagToFeed);
    }

    public List<FeedTag> findFeedTags(Feed feed) {
        List<FeedTag> feedTags = new ArrayList<>();
        feed.getTagToFeeds().forEach(tagToFeed -> feedTags.add(tagToFeed.getFeedTag()));
        return feedTags;
    }

    public List<FeedTag> updateTags(Feed feed, List<FeedTag> feedTags) {
        List<TagToFeed> findTagToFeeds = tagToFeedRepository.findByFeed(feed);
        List<FeedTag> updateFeedTags = new ArrayList<>();

        // feedTag ?????? ????????? ?????? ?????? feed??? ??????????????? TagToCat ?????? ?????? ??????
        if (feedTags == null) {
            if (!findTagToFeeds.isEmpty()) {
                findTagToFeeds.forEach(this::removeTagToFeed);
            }
            return null;
        }

        // ????????? feedTag??? FeedTag ?????? ??? TagToFeed ??????
        feedTags.forEach(
                feedTag -> {
                    // ???????????? ?????? FeedTag, TagToFeed ????????????
                    FeedTag updateFeedTag = createTag(feedTag);
                    updateTagToFeed(new TagToFeed(feed, updateFeedTag));
                    updateFeedTags.add(updateFeedTag);
                }
        );

        // ????????? ????????? Tag ???????????? TagToFeed ??????
        feed.getTagToFeeds().stream().forEach(
                tagToFeed -> {
                    if (!feedTags.contains(tagToFeed.getFeedTag())) {
                        removeTagToFeed(tagToFeed);
                    }
                }
        );
        return updateFeedTags;
    }

    private TagToFeed updateTagToFeed(TagToFeed tagToFeed) {
        // db??? ????????? ?????? ?????? id ????????? ??????
        TagToFeedId id = new TagToFeedId(tagToFeed.getFeed().getFeedId(), tagToFeed.getFeedTag().getFeedTagId());
        Optional<TagToFeed> optionalTagToFeed = tagToFeedRepository.findById(id);
        return optionalTagToFeed.orElse(tagToFeedRepository.save(tagToFeed));
    }

    public void removeTagToFeed(TagToFeed tagToFeed) {
        tagToFeedRepository.delete(tagToFeed);
    }
}
