package com.twentyfour_seven.catvillage.feed.service;

import com.twentyfour_seven.catvillage.cat.entity.Cat;
import com.twentyfour_seven.catvillage.cat.service.CatService;
import com.twentyfour_seven.catvillage.common.like.LikeService;
import com.twentyfour_seven.catvillage.common.picture.entity.Picture;
import com.twentyfour_seven.catvillage.common.picture.service.PictureService;
import com.twentyfour_seven.catvillage.exception.BusinessLogicException;
import com.twentyfour_seven.catvillage.exception.ExceptionCode;
import com.twentyfour_seven.catvillage.feed.entity.Feed;
import com.twentyfour_seven.catvillage.feed.repository.FeedRepository;
import com.twentyfour_seven.catvillage.user.entity.User;
import com.twentyfour_seven.catvillage.user.service.FollowService;
import com.twentyfour_seven.catvillage.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FeedService {
    private final FeedRepository feedRepository;
    private final CatService catService;
    private final PictureService pictureService;
    private final UserService userService;
    private final FollowService followService;
    private final LikeService likeService;

    public FeedService(FeedRepository feedRepository,
                       CatService catService,
                       PictureService pictureService,
                       UserService userService,
                       FollowService followService,
                       LikeService likeService) {
        this.feedRepository = feedRepository;
        this.catService = catService;
        this.pictureService = pictureService;
        this.userService = userService;
        this.followService = followService;
        this.likeService = likeService;
    }

    public Feed createFeed(Feed feed, long catId, String email) {
        // ????????? ????????? ???????????? ???????????? ??????
        Cat findCat = catService.findVerifiedCat(catId);
        User user = findCat.getUser();

        // ???????????? ????????? ???????????? ???????????? ??????
        if (!user.getEmail().equals(email)) {
            throw new BusinessLogicException(ExceptionCode.INVALID_USER);
        }

        // feed??? ????????? ?????? ??????
        feed.setCat(findCat);

        // Picture ??????
        feed.getPictures().forEach(
                picture ->
                        pictureService.createPicture(
                                Picture.builder()
                                        .path(picture.getPath())
                                        .feed(feed)
                                        .build())
        );

        // feed ?????? ??? ??????
        Feed saveFeed = feedRepository.save(feed);

        // ????????? contentCount +1 ?????? ??????
        userService.addContentCount(user);

        return saveFeed;
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

    public List<Cat> findFollows(String email) {
        User loginUser = userService.findVerifiedEmail(email);
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

    public Feed updateFeed(long feedId, Feed feed, String email) {
        // feedId??? ??????????????? feed ????????? ?????????
        Feed findFeed = findVerifiedFeed(feedId);

        // ?????? ?????? ????????? ????????? ???????????? ????????? ???????????? ???????????? ????????? Exception throw
        if (!findFeed.getCat().getUser().getEmail().equals(email)) {
            throw new BusinessLogicException(ExceptionCode.INVALID_USER);
        }

        // Feed ?????? ????????????
        findFeed.setBody(feed.getBody());

        // Picture ??????????????? ?????? source, definition??? path??? ?????? ??????
        List<String> picturePaths1 = feed.getPictures().stream()
                .map(picture -> picture.getPath()).collect(Collectors.toList());
        List<String> picturePaths2 = findFeed.getPictures().stream()
                .map(picture -> picture.getPath()).collect(Collectors.toList());

        // ????????? ???????????? DB?????? ?????? ??? ?????????????????? ??????
        findFeed.getPictures().forEach(
                picture -> {
                    if (!picturePaths1.contains(picture.getPath())) {
                        pictureService.removePicture(picture.getPictureId());
                    }
                }
        );
        findFeed.getPictures().removeIf(picture -> !picturePaths1.contains(picture.getPath()));

        // ????????? ???????????? DB??? ?????? ??? ??????????????? ??????
        feed.getPictures().forEach(
                picture -> {
                    if (!picturePaths2.contains(picture.getPath())) {
                        Picture createPicture = Picture.builder().feed(findFeed).path(picture.getPath()).build();
                        findFeed.getPictures().add(pictureService.createPicture(createPicture));
                    }
                }
        );
        return feedRepository.save(findFeed);
    }

    public void removeFeed(long feedId, String email) {
        Feed findFeed = findVerifiedFeed(feedId);
        User findUser = userService.findVerifiedEmail(email);
        // feed??? ????????? ????????? ????????? ?????? ????????? ????????? INVALID_USER Exception throw
        if (!findFeed.getCat().getUser().getUserId().equals(findUser.getUserId())) {
            throw new BusinessLogicException(ExceptionCode.INVALID_USER);
        }
        feedRepository.delete(findFeed);

        // ????????? contentCount - 1 ?????? ??????
        userService.removeContentCount(findUser);
    }

    public void addLike(long feedId, String email) {
        // feed id ??? ???????????? ???????????? ???????????? ???????????? ?????????
        Feed findFeed = findVerifiedFeed(feedId);
        User findUser = userService.findVerifiedEmail(email);

        // like ??????
        likeService.addLikeInFeed(findUser, findFeed);

        // feed ??? likeCount 1 ????????? ??????
        findFeed.setLikeCount(findFeed.getLikeCount() + 1);
        feedRepository.save(findFeed);
    }

    public void deleteLike(long feedId, String email) {
        // feed id ??? ???????????? ???????????? ???????????? ?????? ?????? ?????????
        Feed findFeed = findVerifiedFeed(feedId);
        User findUser = userService.findVerifiedEmail(email);

        // like ??????
        likeService.deleteLikeInFeed(findUser, findFeed);

        // feed ??? likeCount 1 ?????? ??????
        findFeed.setLikeCount(findFeed.getLikeCount() - 1);
        feedRepository.save(findFeed);
    }

    public boolean feedIsLike(Feed feed, String email) {
        User findUser = userService.findVerifiedEmail(email);
        return likeService.findExistLike(feed, findUser);
    }
}
