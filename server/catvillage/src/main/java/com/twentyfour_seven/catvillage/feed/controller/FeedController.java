package com.twentyfour_seven.catvillage.feed.controller;

import com.twentyfour_seven.catvillage.feed.dto.*;
import com.twentyfour_seven.catvillage.feed.entity.Feed;
import com.twentyfour_seven.catvillage.feed.entity.FeedComment;
import com.twentyfour_seven.catvillage.feed.entity.FeedTag;
import com.twentyfour_seven.catvillage.feed.mapper.FeedCommentMapper;
import com.twentyfour_seven.catvillage.feed.mapper.FeedMapper;
import com.twentyfour_seven.catvillage.feed.mapper.FeedTagMapper;
import com.twentyfour_seven.catvillage.feed.service.FeedCommentService;
import com.twentyfour_seven.catvillage.feed.service.FeedService;
import com.twentyfour_seven.catvillage.feed.service.FeedTagService;
import com.twentyfour_seven.catvillage.user.dto.FollowFeedGetDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity postFeed(@RequestBody @Valid FeedPostDto feedPostDto) {
        Feed feed = feedMapper.feedPostDtoToFeed(feedPostDto);
        Feed createFeed = feedService.createFeed(feed, feedPostDto.getCatId());
        List<FeedTag> feedTags = feedTagMapper.feedTagDtosToFeedTags(feedPostDto.getTags());
        List<FeedTag> createFeedTags = feedTagService.createTags(createFeed, feedTags);
        return new ResponseEntity<>(feedMapper.feedToFeedResponseDto(createFeed), HttpStatus.CREATED);
    }

    @Operation(summary = "냥이생활 특정 피드 보기")
    @GetMapping("/{feeds-id}")
    public ResponseEntity getFeed(@PathVariable("feeds-id") @Positive long feedId) {
        Feed feed = feedService.findFeed(feedId);
        List<FeedTag> feedTags = feedTagService.findFeedTags(feed);
        List<FeedComment> comments = feedCommentService.findComments(feed);
        FeedGetResponseDto response = feedMapper.feedToFeedGetResponseDto(feed);
        response.setTags(feedTagMapper.feedTagsToFeedTagDtos(feedTags));
        response.setComments(feedCommentMapper.feedCommentsToFeedCommentGetDtos(comments));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "냥이생활 전체 게시글 보기")
    @GetMapping
    public ResponseEntity getFeeds(@RequestParam @Positive int page,
                                   @RequestParam @Positive int size,
                                   @AuthenticationPrincipal User user) {
        // page 와 size 정보를 토대로 Page 생성
        Page<Feed> feeds = feedService.findFeeds(page - 1, size);

        // Page 의 feed 컨텐츠를 형식에 맞는 Dto 로 매핑
        List<FeedMultiGetResponseDto> feedResponseDto = feedMapper.feedsToFeedMultiGetResponseDtos(feeds.getContent());

        // TODO: Like 구현 후 로그인한 유저가 글마다 좋아요를 눌렀는지 여부 feedResponseDto 에 반영 로직 필요 !

        // 응답 객체 생성
        FeedMultiResponseDto response = new FeedMultiResponseDto(feedResponseDto, feeds);

        // 로그인 정보가 있다면 유저가 팔로우한 유저의 고양이 정보 가져오기
        if (user != null) {
            List<FollowFeedGetDto> follows = feedService.findFollows(user).stream().map(
                    cat -> new FollowFeedGetDto(cat.getImage(), cat.getCatId())
            ).collect(Collectors.toList());
            response.setFollow(follows);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "냥이생활 작성한 글 수정하기", description = "로그인한 유저와 글을 작성했던 유저가 다르면 에러가 납니다.")
    @PatchMapping("{feeds-id}")
    public ResponseEntity patchFeed(@PathVariable("feeds-id") @Positive long feedsId,
                                    @RequestBody @Valid FeedPostDto feedPostDto,
                                    @AuthenticationPrincipal User user) {
        // feedService의 updateFeed 메서드에서 feed, pictures update
        Feed updateFeed = feedService.updateFeed(feedMapper.feedPostDtoToFeed(feedPostDto), user.getUsername());

        // FeedPostDto의 FeedTagDto 리스트를 FeedTag 리스트로 변환
        List<FeedTag> feedTags = feedTagMapper.feedTagDtosToFeedTags(feedPostDto.getTags());

        // feedTagService의 메서드에서 FeedTag와 TagToFeed update
        List<FeedTag> updateTags = feedTagService.updateTags(updateFeed, feedTags);

        // responseDto 생성하여 값 대입
        FeedResponseDto response = feedMapper.feedToFeedResponseDto(updateFeed);
        response.setTags(feedTagMapper.feedTagsToFeedTagDtos(updateTags));
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
