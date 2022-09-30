package com.twentyfour_seven.catvillage.feed.service;

import com.twentyfour_seven.catvillage.cat.entity.Cat;
import com.twentyfour_seven.catvillage.cat.service.CatService;
import com.twentyfour_seven.catvillage.common.picture.service.PictureService;
import com.twentyfour_seven.catvillage.exception.BusinessLogicException;
import com.twentyfour_seven.catvillage.exception.ExceptionCode;
import com.twentyfour_seven.catvillage.feed.entity.Feed;
import com.twentyfour_seven.catvillage.feed.repository.FeedRepository;
import com.twentyfour_seven.catvillage.user.controller.FollowService;
import com.twentyfour_seven.catvillage.user.entity.User;
import com.twentyfour_seven.catvillage.user.repository.UserRepository;
import com.twentyfour_seven.catvillage.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FeedService {
    private final FeedRepository feedRepository;
    private final CatService catService;
    private final PictureService pictureService;
    private final UserService userService;
    private final FollowService followService;

    public FeedService(FeedRepository feedRepository,
                       CatService catService,
                       PictureService pictureService,
                       UserService userService,
                       FollowService followService) {
        this.feedRepository = feedRepository;
        this.catService = catService;
        this.pictureService = pictureService;
        this.userService = userService;
        this.followService = followService;
    }

    public Feed createFeed(Feed feed, long catId) {
        Cat findCat = catService.findVerifiedCat(catId);
        feed.setCat(findCat);
        feed.getPictures().forEach(
                picture -> pictureService.createPicture(picture)
        );

        return feedRepository.save(feed);
    }

    public Feed findFeed(long feedId) {
        return findVerifiedFeed(feedId);
    }

    private Feed findVerifiedFeed(long feedId) {
        Optional<Feed> optionalFeed = feedRepository.findById(feedId);
        Feed findFeed = optionalFeed
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.FEED_NOT_FOUND));
        return findFeed;
    }

    public Page<Feed> findFeeds(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("feedId").descending());
        return feedRepository.findAll(pageRequest);
    }

    public List<Cat> findFollows(org.springframework.security.core.userdetails.User user) {
        User loginUser = userService.findVerifiedEmail(user.getUsername());
        List<User> followUsers = followService.findFollowsInFeed(loginUser);
        List<Cat> followCats = new ArrayList<>();
        followUsers.forEach(
                followUser -> {
                    followUser.getCats().forEach(
                            cat -> followCats.add(cat)
                    );
                }
        );
        return followCats;
    }
}
