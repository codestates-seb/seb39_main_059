package com.twentyfour_seven.catvillage.feed.controller;

import com.twentyfour_seven.catvillage.feed.dto.FeedGetResponseDto;
import com.twentyfour_seven.catvillage.feed.dto.FeedPostDto;
import com.twentyfour_seven.catvillage.feed.entity.Feed;
import com.twentyfour_seven.catvillage.feed.entity.FeedComment;
import com.twentyfour_seven.catvillage.feed.entity.FeedTag;
import com.twentyfour_seven.catvillage.feed.mapper.FeedCommentMapper;
import com.twentyfour_seven.catvillage.feed.mapper.FeedMapper;
import com.twentyfour_seven.catvillage.feed.mapper.FeedTagMapper;
import com.twentyfour_seven.catvillage.feed.service.FeedCommentService;
import com.twentyfour_seven.catvillage.feed.service.FeedService;
import com.twentyfour_seven.catvillage.feed.service.FeedTagService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
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
    private final FeedCommentMapper feedCommentMapper;

    public FeedController(FeedService feedService, FeedMapper feedMapper,
                          FeedCommentService feedCommentService,
                          FeedTagService feedTagService,
                          FeedTagMapper feedTagMapper,
                          FeedCommentMapper feedCommentMapper) {
        this.feedService = feedService;
        this.feedMapper = feedMapper;
        this.feedCommentService = feedCommentService;
        this.feedTagService = feedTagService;
        this.feedTagMapper = feedTagMapper;
        this.feedCommentMapper = feedCommentMapper;
    }

    @Operation(summary = "냥이생활 피드 작성하기")
    @PostMapping
    public ResponseEntity postFeed (@RequestBody @Valid FeedPostDto feedPostDto) {
        Feed feed = feedMapper.feedPostDtoToFeed(feedPostDto);
        Feed createFeed = feedService.createFeed(feed, feedPostDto.getCatId());
        List<FeedTag> feedTags = feedTagMapper.feedTagDtosToFeedTags(feedPostDto.getTags());
        List<FeedTag> createFeedTags = feedTagService.createTags(createFeed, feedTags);
        return new ResponseEntity<>(feedMapper.feedToFeedResponseDto(createFeed), HttpStatus.CREATED);
    }

    @GetMapping("/{feeds-id}")
    public ResponseEntity getFeed (@PathVariable("feeds-id") @Positive long feedId) {
        Feed feed = feedService.findFeed(feedId);
        List<FeedTag> feedTags  = feedTagService.findFeedTags(feed);
        List<FeedComment> comments = feedCommentService.findComments(feed);
        FeedGetResponseDto response = feedMapper.feedToFeedGetResponseDto(feed);
        response.setTags(feedTagMapper.feedTagsToFeedTagDtos(feedTags));
        response.setComments(feedCommentMapper.feedCommentsToFeedCommentGetDtos(comments));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
