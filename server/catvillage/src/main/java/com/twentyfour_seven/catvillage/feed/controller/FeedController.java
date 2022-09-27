package com.twentyfour_seven.catvillage.feed.controller;

import com.twentyfour_seven.catvillage.feed.dto.FeedPostDto;
import com.twentyfour_seven.catvillage.feed.entity.Feed;
import com.twentyfour_seven.catvillage.feed.entity.FeedTag;
import com.twentyfour_seven.catvillage.feed.mapper.FeedMapper;
import com.twentyfour_seven.catvillage.feed.mapper.FeedTagMapper;
import com.twentyfour_seven.catvillage.feed.service.FeedCommentService;
import com.twentyfour_seven.catvillage.feed.service.FeedService;
import com.twentyfour_seven.catvillage.feed.service.FeedTagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/냥이생활")
@Transactional
@Validated
public class FeedController {
    private final FeedService feedService;
    private final FeedCommentService feedCommentService;
    private final FeedTagService feedTagService;
    private final FeedMapper feedMapper;
    private final FeedTagMapper feedTagMapper;

    public FeedController(FeedService feedService, FeedMapper feedMapper,
                          FeedCommentService feedCommentService,
                          FeedTagService feedTagService,
                          FeedTagMapper feedTagMapper) {
        this.feedService = feedService;
        this.feedMapper = feedMapper;
        this.feedCommentService = feedCommentService;
        this.feedTagService = feedTagService;
        this.feedTagMapper = feedTagMapper;
    }

    @PostMapping
    public ResponseEntity postFeed (@RequestBody @Valid FeedPostDto feedPostDto) {
        Feed feed = feedMapper.feedPostDtoToFeed(feedPostDto);
        Feed createFeed = feedService.createFeed(feed, feedPostDto.getCatId());
        List<FeedTag> feedTags = feedTagMapper.feedTagDtosToFeedTags(feedPostDto.getTags());
        List<FeedTag> createFeedTags = feedTagService.createTags(createFeed, feedTags);
        return new ResponseEntity<>(feedMapper.feedToFeedResponseDto(createFeed), HttpStatus.CREATED);
    }
}
