package com.twentyfour_seven.catvillage.feed.service;

import com.twentyfour_seven.catvillage.cat.entity.Cat;
import com.twentyfour_seven.catvillage.cat.service.CatService;
import com.twentyfour_seven.catvillage.common.like.LikeService;
import com.twentyfour_seven.catvillage.exception.BusinessLogicException;
import com.twentyfour_seven.catvillage.exception.ExceptionCode;
import com.twentyfour_seven.catvillage.feed.entity.Feed;
import com.twentyfour_seven.catvillage.feed.entity.FeedComment;
import com.twentyfour_seven.catvillage.feed.repository.FeedCommentRepository;
import com.twentyfour_seven.catvillage.user.entity.User;
import com.twentyfour_seven.catvillage.user.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedCommentService {
    private final FeedCommentRepository feedCommentRepository;
    private final UserService userService;
    private final CatService catService;
    private final LikeService likeService;

    public FeedCommentService(FeedCommentRepository feedCommentRepository,
                              UserService userService,
                              CatService catService, LikeService likeService) {
        this.feedCommentRepository = feedCommentRepository;
        this.userService = userService;
        this.catService = catService;
        this.likeService = likeService;
    }

    public List<FeedComment> findComments(Feed feed) {
        List<FeedComment> feedComments = feedCommentRepository.findByFeed(feed);
        return feedComments;
    }

    public FeedComment createComment(FeedComment feedComment, String email) {
        verifiedUser(feedComment, email);

        return feedCommentRepository.save(feedComment);
    }

    public FeedComment updateComment(long commentId, FeedComment feedComment, String email) {
        // 댓글을 작성했던 유저와 요청을 보낸 유저가 동일한지 확인
        FeedComment findComment = findVerifiedComment(commentId);
        verifiedUser(findComment, email);

        findComment.setBody(feedComment.getBody());

        return feedCommentRepository.save(findComment);
    }

    public void removeComment(long commentId, String email) {
        FeedComment findFeedComment = findVerifiedComment(commentId);
        verifiedUser(findFeedComment, email);
        feedCommentRepository.delete(findFeedComment);
    }

    public FeedComment findVerifiedComment(long commentId) {
        Optional<FeedComment> optionalComment = feedCommentRepository.findById(commentId);
        return optionalComment.orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
    }

    // 댓글을 작성하려는 유저가 요청을 보낸 유저와 일치하는지 검증
    private void verifiedUser(FeedComment feedComment, String email) {
        User user = userService.findVerifiedEmail(email);

        // feedComment 에 유저(고양이) id만 담겨있으므로 id로만 조회
        if (feedComment.getCat() == null && !feedComment.getUser().getUserId().equals(user.getUserId())) {
            throw new BusinessLogicException(ExceptionCode.INVALID_USER);
        } else if (feedComment.getUser() == null) {
            Cat cat = catService.findVerifiedCat(feedComment.getCat().getCatId());
            if (!cat.getUser().getEmail().equals(email)) {
                throw new BusinessLogicException(ExceptionCode.INVALID_USER);
            }
        }
    }

    public void addLike(long commentsId, String email) {
        // comment id 로 존재하는 피드인지 확인하고 피드정보 가져옴
        FeedComment findComment = findVerifiedComment(commentsId);
        User findUser = userService.findVerifiedEmail(email);

        // like 추가
        likeService.addLikeInFeedComment(findUser, findComment);

        // FeedComment 에 likeCount 1 올려서 저장
        findComment.setLikeCount(findComment.getLikeCount() + 1);
        feedCommentRepository.save(findComment);
    }

    public void deleteLike(long commentId, String email) {
        // feed id 로 존재하는 피드인지 확인하고 피드 정보 가져옴
        FeedComment findComment = findVerifiedComment(commentId);
        User findUser = userService.findVerifiedEmail(email);

        // like 삭제
        likeService.deleteLikeInFeedComment(findUser, findComment);

        // feed 에 likeCount 1 빼서 저장
        findComment.setLikeCount(findComment.getLikeCount() - 1);
        feedCommentRepository.save(findComment);
    }
}
