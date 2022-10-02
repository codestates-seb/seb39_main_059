package com.twentyfour_seven.catvillage.feed.service;

import com.twentyfour_seven.catvillage.cat.entity.Cat;
import com.twentyfour_seven.catvillage.cat.service.CatService;
import com.twentyfour_seven.catvillage.exception.BusinessLogicException;
import com.twentyfour_seven.catvillage.exception.ExceptionCode;
import com.twentyfour_seven.catvillage.feed.entity.Feed;
import com.twentyfour_seven.catvillage.feed.entity.FeedComment;
import com.twentyfour_seven.catvillage.feed.repository.FeedCommentRepository;
import com.twentyfour_seven.catvillage.user.entity.User;
import com.twentyfour_seven.catvillage.user.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedCommentService {
    private final FeedCommentRepository feedCommentRepository;
    private final UserService userService;
    private final CatService catService;

    public FeedCommentService(FeedCommentRepository feedCommentRepository,
                              UserService userService,
                              CatService catService) {
        this.feedCommentRepository = feedCommentRepository;
        this.userService = userService;
        this.catService = catService;
    }

    public List<FeedComment> findComments(Feed feed) {
        List<FeedComment> feedComments = feedCommentRepository.findByFeed(feed);
        return feedComments;
    }

    public FeedComment createComment(FeedComment feedComment, String email) {
        // 댓글을 작성하려는 유저가 request body 의 유저와 일치하는지 검증
        User user = userService.findVerifiedEmail(email);
        // feedComment 에 유저(고양이) id만 담겨있으므로 id로만 조회
        if (feedComment.getCat() == null && !feedComment.getUser().getUserId().equals(user.getUserId())) {
             throw new BusinessLogicException(ExceptionCode.INVALID_USER);
        } else if (feedComment.getUser() == null){
            Cat cat = catService.findVerifiedCat(feedComment.getCat().getCatId());
            if (!cat.getUser().getEmail().equals(email)) {
                throw new BusinessLogicException(ExceptionCode.INVALID_USER);
            }
        }

        return feedCommentRepository.save(feedComment);
    }
}
