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

        // feedTag 값이 하나도 없을 경우 feed에 연결되있던 TagToCat 객체 모두 삭제
        if (feedTags == null) {
            if (!findTagToFeeds.isEmpty()) {
                findTagToFeeds.forEach(this::removeTagToFeed);
            }
            return null;
        }

        // 추가한 feedTag는 FeedTag 생성 및 TagToFeed 생성
        feedTags.forEach(
                feedTag -> {
                    // 입력받은 모든 FeedTag, TagToFeed 업데이트
                    FeedTag updateFeedTag = createTag(feedTag);
                    updateTagToFeed(new TagToFeed(feed, updateFeedTag));
                    updateFeedTags.add(updateFeedTag);
                }
        );

        // 유저가 제거한 Tag 확인하여 TagToFeed 제거
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
        // db에 있는지 찾기 위해 id 클래스 생성
        TagToFeedId id = new TagToFeedId(tagToFeed.getFeed().getFeedId(), tagToFeed.getFeedTag().getFeedTagId());
        Optional<TagToFeed> optionalTagToFeed = tagToFeedRepository.findById(id);
        return optionalTagToFeed.orElse(tagToFeedRepository.save(tagToFeed));
    }

    public void removeTagToFeed(TagToFeed tagToFeed) {
        tagToFeedRepository.delete(tagToFeed);
    }
}
