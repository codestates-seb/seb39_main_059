package com.twentyfour_seven.catvillage.feed.service;

import com.twentyfour_seven.catvillage.feed.entity.Feed;
import com.twentyfour_seven.catvillage.feed.entity.FeedTag;
import com.twentyfour_seven.catvillage.feed.entity.TagToFeed;
import com.twentyfour_seven.catvillage.feed.entity.TagToFeedId;
import com.twentyfour_seven.catvillage.feed.repository.FeedTagRepository;
import com.twentyfour_seven.catvillage.feed.repository.TagToFeedRepository;
import com.twentyfour_seven.catvillage.utils.CustomBeanUtils;
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

    public List<FeedTag> updateTags(Feed feed, List<FeedTag> feedTags) {
        feedTags.forEach(
                feedTag -> {
                    // 입력받은 모든 FeedTag, TagToFeed 업데이트
                    feedTag = updateFeedTag(feedTag);
                    updateTagToFeed(new TagToFeed(feed, feedTag));
                }
        );
        // 유저가 제거한 Tag 확인하여 TagToFeed 제거
        feed.getTagToFeeds().stream().forEach(
                tagToFeed -> {
                    if (!feedTags.contains(tagToFeed.getFeedTag())) {
                        tagToFeedRepository.delete(tagToFeed);
                    }
                }
        );
        return feedTags;
    }

    private FeedTag updateFeedTag(FeedTag feedTag) {
        // feedTag 가 DB에 있는지 확인
        Optional<FeedTag> optionalFeedTag = feedTagRepository.findByTagName(feedTag.getTagName());
        // DB에 없다면 feedTag 저장
        if(!optionalFeedTag.isPresent()) {
            return feedTagRepository.save(feedTag);
        }
        return feedTag;
    }

    private TagToFeed updateTagToFeed(TagToFeed tagToFeed) {
        // db에 있는지 찾기 위해 id 클래스 생성
        TagToFeedId id = new TagToFeedId(tagToFeed.getFeed().getFeedId(), tagToFeed.getFeedTag().getFeedTagId());
        Optional<TagToFeed> optionalTagToFeed = tagToFeedRepository.findById(id);
        return optionalTagToFeed.orElse(tagToFeedRepository.save(tagToFeed));
    }
}
