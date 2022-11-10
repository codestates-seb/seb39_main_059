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
import com.twentyfour_seven.catvillage.user.dto.follow.FollowFeedGetDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

//@Tag(name = "Feed", description = "냥이생활 API")
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

    @Operation(summary = "냥이생활 피드 작성하기",
            responses = {
                    @ApiResponse(responseCode = "201", description = "냥이생활 피드 등록 성공", content = @Content(schema = @Schema(implementation = FeedResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 고양이 정보")
            })
    @PostMapping
    public ResponseEntity postFeed(@RequestBody @Valid FeedPostDto feedPostDto,
                                   @AuthenticationPrincipal User user) {
        Feed feed = feedMapper.feedPostDtoToFeed(feedPostDto);
        Feed createFeed = feedService.createFeed(feed, feedPostDto.getCatId(), user.getUsername());

        List<FeedTag> feedTags = feedTagMapper.feedTagDtosToFeedTags(feedPostDto.getTags());
        List<FeedTag> createFeedTags = feedTagService.createTags(createFeed, feedTags);

        FeedResponseDto feedResponseDto = feedMapper.feedToFeedResponseDto(createFeed);
        feedResponseDto.setTags(feedTagMapper.feedTagsToFeedTagDtos(createFeedTags));
        return new ResponseEntity<>(feedResponseDto, HttpStatus.CREATED);
    }

    @Operation(summary = "냥이생활 특정 피드 보기",
            responses = {
                    @ApiResponse(responseCode = "200", description = "냥이생활 피드 정보 조회 성공", content = @Content(schema = @Schema(implementation = FeedGetResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 냥이생활 피드")
            })
    @GetMapping("/{feeds-id}")
    public ResponseEntity getFeed(@PathVariable("feeds-id") @Positive long feedId,
                                  @AuthenticationPrincipal User user) {
        Feed findFeed = feedService.findFeed(feedId);
        List<FeedTag> feedTags = feedTagService.findFeedTags(findFeed);
        List<FeedComment> comments = feedCommentService.findComments(findFeed);

        FeedGetResponseDto response = feedMapper.feedToFeedGetResponseDto(findFeed, user != null ? user.getUsername() : null);

        // 로그인되어 있다면 유저가 해당 피드에 좋아요를 눌렀는지 확인
        if (user != null) {
            boolean isLike = feedService.feedIsLike(findFeed, user.getUsername());
            response.setIsLike(isLike);
        }

        response.setTags(feedTagMapper.feedTagsToFeedTagDtos(feedTags));
        response.setComments(feedCommentMapper.feedCommentsToFeedCommentGetDtos(comments));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "냥이생활 전체 게시글 보기",
            responses = {
                    @ApiResponse(responseCode = "200", description = "전체 냥이생활 피드 조회 성공", content = @Content(schema = @Schema(implementation = FeedMultiResponseDto.class)))
            })
    @GetMapping
    public ResponseEntity getFeeds(@RequestParam @Positive int page,
                                   @RequestParam @Positive int size,
                                   @AuthenticationPrincipal User user) {
        // page 와 size 정보를 토대로 Page 생성
        Page<Feed> feedPage = feedService.findFeeds(page - 1, size);

        String email = null;
        if (user != null) {
            email = user.getUsername();
        }

        // feed, userId로 feed dto 리스트로 반환
        List<FeedMultiGetResponseDto> feedResponseDtos =
                feedMapper.feedsToFeedMultiGetResponseDtos(feedPage.getContent(), email);

        // Page 의 feed 컨텐츠를 형식에 맞는 Dto 로 매핑


        // 응답 객체 생성
        FeedMultiResponseDto response = new FeedMultiResponseDto(feedResponseDtos, feedPage);

        // 로그인 정보가 있다면 유저가 팔로우한 유저의 고양이 정보 가져오기
        if (user != null) {
            List<FollowFeedGetDto> follows = feedService.findFollows(email).stream().map(
                    cat -> new FollowFeedGetDto(cat.getImage(), cat.getCatId())
            ).collect(Collectors.toList());

            response.setFollow(follows);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "냥이생활 작성한 글 수정하기", description = "로그인한 유저와 글을 작성했던 유저가 다르면 에러가 납니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "냥이생활 피드 수정 성공", content = @Content(schema = @Schema(implementation = FeedResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 피드"),
                    @ApiResponse(responseCode = "405", description = "유저 정보 불일치")
            })
    @PatchMapping("/{feeds-id}")
    public ResponseEntity patchFeed(@PathVariable("feeds-id") @Positive long feedId,
                                    @RequestBody @Valid FeedPostDto feedPostDto,
                                    @AuthenticationPrincipal User user) {
        // feedService의 updateFeed 메서드에서 feed, pictures update
        Feed feed = feedMapper.feedPostDtoToFeed(feedPostDto);
        Feed updateFeed = feedService.updateFeed(feedId, feed, user.getUsername());

        // FeedPostDto의 FeedTagDto 리스트를 FeedTag 리스트로 변환
        List<FeedTag> feedTags = feedTagMapper.feedTagDtosToFeedTags(feedPostDto.getTags());

        // feedTagService의 메서드에서 FeedTag와 TagToFeed update
        List<FeedTag> updateTags = feedTagService.updateTags(updateFeed, feedTags);

        // responseDto 생성하여 값 대입
        FeedResponseDto response = feedMapper.feedToFeedResponseDto(updateFeed);
        response.setTags(feedTagMapper.feedTagsToFeedTagDtos(updateTags));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "냥이생활 작성한 글 삭제하기", description = "로그인한 유저와 글을 작성한 유저가 다를 경우: 405에러, 글이 존재하지 않을 경우: 409",
            responses = {
                    @ApiResponse(responseCode = "204", description = "냥이생활 피드 삭제 성공"),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글"),
                    @ApiResponse(responseCode = "405", description = "유저 정보 불일치")
            })
    @DeleteMapping("/{feeds-id}")
    public ResponseEntity deleteFeed(@PathVariable("feeds-id") @Positive long feedId,
                                     @AuthenticationPrincipal User user) {
        // 이메일과 feedId 서비스에 전달
        feedService.removeFeed(feedId, user.getUsername());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "냥이생활 댓글 작성하기", description = "댓글을 작성한 유저와 요청으로 보낸 profileId(유저, 고양이)의 유저가 다를 경우 에러 발생",
            responses = {
                    @ApiResponse(responseCode = "201", description = "냥이생활 댓글 작성 성공",
                            content = @Content(schema = @Schema(implementation = FeedCommentGetDto.class))),
                    @ApiResponse(responseCode = "405", description = "유저 정보 불일치")
            }
    )
    @PostMapping("/{feeds-id}/comments")
    public ResponseEntity postFeedComment(@PathVariable("feeds-id") @Positive long feedId,
                                          @RequestBody @Valid FeedCommentPostDto feedCommentPostDto,
                                          @AuthenticationPrincipal User user) {
        // feedId, profileId, body 저장하여 feedComment 생성
        FeedComment feedComment = feedCommentMapper.feedCommentPostDtoToFeedComment(feedId, feedCommentPostDto);

        FeedComment createFeedComment = feedCommentService.createComment(feedComment, user.getUsername());
        return new ResponseEntity<>(feedCommentMapper.feedCommentToFeedCommentGetDto(createFeedComment),
                HttpStatus.CREATED);
    }

    @Operation(summary = "냥이생활 댓글 수정하기", description = "댓글을 작성한 유저와 요청을 보낸 유저가 다를 경우 에러가 발생, 존재하지 않는 댓글일 경우 에러 발생",
            responses = {
                    @ApiResponse(responseCode = "200", description = "냥이생활 댓글 수정 성공",
                            content = @Content(schema = @Schema(implementation = FeedCommentGetDto.class))),
                    @ApiResponse(responseCode = "405", description = "유저 정보 불일치"),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 댓글")
            }
    )
    @PatchMapping("/comments/{comments-id}")
    public ResponseEntity patchFeedComment(@PathVariable("comments-id") @Positive long commentId,
                                           @RequestBody @Valid FeedCommentPatchDto feedCommentPatchDto,
                                           @AuthenticationPrincipal User user) {
        FeedComment feedComment = feedCommentMapper.feedCommentPatchDtoToFeedComment(feedCommentPatchDto);

        FeedComment updateFeedComment = feedCommentService.updateComment(commentId, feedComment, user.getUsername());
        return new ResponseEntity<>(feedCommentMapper.feedCommentToFeedCommentGetDto(updateFeedComment), HttpStatus.OK);
    }

    @DeleteMapping("/comments/{comments-id}")
    public ResponseEntity deleteFeedComment(@PathVariable("comments-id") @Positive long commentId,
                                            @AuthenticationPrincipal User user) {
        feedCommentService.removeComment(commentId, user.getUsername());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "냥이생활 피드에 좋아요 추가", description = "로그인한 유저 정보를 가져와서 해당 피드에 좋아요를 추가합니다.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "좋아요 등록 성공"),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 피드"),
                    @ApiResponse(responseCode = "409", description = "이미 좋아요가 존재")
            })
    @PostMapping("/{feeds-id}/like")
    public ResponseEntity postLikeInFeed(@PathVariable("feeds-id") @Positive long feedId,
                                         @AuthenticationPrincipal User user) {
        feedService.addLike(feedId, user.getUsername());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "냥이생활 피드에 좋아요 삭제", description = "로그인한 유저 정보를 가져와서 해당 피드에 좋아요를 삭제합니다.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "좋아요 삭제 성공"),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 피드"),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 좋아요")
            })
    @DeleteMapping("/{feeds-id}/like")
    public ResponseEntity deleteLikeInFeed(@PathVariable("feeds-id") @Positive long feedId,
                                           @AuthenticationPrincipal User user) {
        feedService.deleteLike(feedId, user.getUsername());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "냥이생활 댓글에 좋아요 추가", description = "로그인한 유저 정보를 가져와서 해당 댓글에 좋아요를 추가합니다.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "좋아요 등록 성공"),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 댓글"),
                    @ApiResponse(responseCode = "409", description = "이미 좋아요가 존재")
            })
    @PostMapping("/comments/{comments-id}/like")
    public ResponseEntity postLikeInComment(@PathVariable("comments-id") @Positive long commentsId,
                                            @AuthenticationPrincipal User user) {
        feedCommentService.addLike(commentsId, user.getUsername());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "냥이생활 댓글에 좋아요 삭제", description = "로그인한 유저 정보를 가져와서 해당 댓글에 좋아요를 삭제합니다.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "좋아요 삭제 성공"),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 댓글"),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 좋아요")
            })
    @DeleteMapping("/comments/{comments-id}/like")
    public ResponseEntity deleteLikeInComment(@PathVariable("comments-id") @Positive long commentId,
                                              @AuthenticationPrincipal User user) {
        feedCommentService.deleteLike(commentId, user.getUsername());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "특정 유저가 작성한 피드 정보 조회", description = "유저 프로필 페이지에 들어갈 정보",
            responses = {
                    @ApiResponse(responseCode = "200", description = "피드 조회 성공",
                            content = @Content(schema = @Schema(implementation = FeedSimpleDto.class))),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 유저")
            }
    )
    @GetMapping("/users/{users-id}")
    public ResponseEntity getFeedFromUser(@PathVariable("users-id") @Positive long userId) {
        List<Feed> feeds = feedService.findFeedByUser(userId);
        return new ResponseEntity<>(feedMapper.feedsToFeedSimpleDtos(feeds), HttpStatus.OK);
    }

    @GetMapping("/users/{users-id}/likes")
    public ResponseEntity getFeedFromUserLike(@PathVariable("users-id") @Positive long userId) {
        List<Feed> feeds = feedService.findFeedByLike(userId);
        return new ResponseEntity<>(feedMapper.feedsToFeedSimpleDtos(feeds), HttpStatus.OK);
    }
}
