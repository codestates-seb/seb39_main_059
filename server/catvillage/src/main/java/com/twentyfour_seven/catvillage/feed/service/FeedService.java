package com.twentyfour_seven.catvillage.feed.service;

import com.twentyfour_seven.catvillage.cat.entity.Cat;
import com.twentyfour_seven.catvillage.cat.service.CatService;
import com.twentyfour_seven.catvillage.common.like.Like;
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
import org.springframework.data.domain.*;
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
        // 작성한 고양이 프로필이 유효한지 확인
        Cat findCat = catService.findVerifiedCat(catId);
        User user = findCat.getUser();

        // 로그인한 유저의 고양이가 아닐경우 에러
        if (!user.getEmail().equals(email)) {
            throw new BusinessLogicException(ExceptionCode.INVALID_USER);
        }

        // feed에 고양이 정보 저장
        feed.setCat(findCat);

        // Picture 저장
        feed.getPictures().forEach(
                picture ->
                        pictureService.createPicture(
                                Picture.builder()
                                        .path(picture.getPath())
                                        .feed(feed)
                                        .build())
        );

        // feed 저장 후 반환
        Feed saveFeed = feedRepository.save(feed);

        // 유저에 contentCount +1 하여 저장
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
        // feedId로 저장되있던 feed 정보를 불러옴
        Feed findFeed = findVerifiedFeed(feedId);

        // 글을 처음 작성한 유저와 로그인한 유저가 동일한지 확인하고 아니면 Exception throw
        if (!findFeed.getCat().getUser().getEmail().equals(email)) {
            throw new BusinessLogicException(ExceptionCode.INVALID_USER);
        }

        // Feed 내용 업데이트
        findFeed.setBody(feed.getBody());

        // Picture 업데이트를 위해 source, definition의 path만 따로 저장
        List<String> picturePaths1 = feed.getPictures().stream()
                .map(picture -> picture.getPath()).collect(Collectors.toList());
        List<String> picturePaths2 = findFeed.getPictures().stream()
                .map(picture -> picture.getPath()).collect(Collectors.toList());

        // 삭제된 이미지는 DB에서 제거 후 리스트에서도 제거
        findFeed.getPictures().forEach(
                picture -> {
                    if (!picturePaths1.contains(picture.getPath())) {
                        pictureService.removePicture(picture.getPictureId());
                    }
                }
        );
        findFeed.getPictures().removeIf(picture -> !picturePaths1.contains(picture.getPath()));

        // 추가된 이미지는 DB에 추가 후 리스트에도 추가
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
        // feed에 저장된 유저와 요청을 보낸 유저가 다르면 INVALID_USER Exception throw
        if (!findFeed.getCat().getUser().getUserId().equals(findUser.getUserId())) {
            throw new BusinessLogicException(ExceptionCode.INVALID_USER);
        }
        feedRepository.delete(findFeed);

        // 유저에 contentCount - 1 하여 저장
        userService.removeContentCount(findUser);
    }

    public void addLike(long feedId, String email) {
        // feed id 로 존재하는 피드인지 확인하고 피드정보 가져옴
        Feed findFeed = findVerifiedFeed(feedId);
        User findUser = userService.findVerifiedEmail(email);

        // like 추가
        likeService.addLikeInFeed(findUser, findFeed);

        // feed 에 likeCount 1 올려서 저장
        findFeed.setLikeCount(findFeed.getLikeCount() + 1);
        feedRepository.save(findFeed);
    }

    public void deleteLike(long feedId, String email) {
        // feed id 로 존재하는 피드인지 확인하고 피드 정보 가져옴
        Feed findFeed = findVerifiedFeed(feedId);
        User findUser = userService.findVerifiedEmail(email);

        // like 삭제
        likeService.deleteLikeInFeed(findUser, findFeed);

        // feed 에 likeCount 1 빼서 저장
        findFeed.setLikeCount(findFeed.getLikeCount() - 1);
        feedRepository.save(findFeed);
    }

    public boolean feedIsLike(Feed feed, String email) {
        User findUser = userService.findVerifiedEmail(email);
        return likeService.findExistLike(feed, findUser);
    }

    public List<Feed> findFeedByUser(long userId) {
        User findUser = userService.findUser(userId);

        List<Feed> findFeeds = new ArrayList<>();
        findUser.getCats().forEach(cat -> findFeeds.addAll(feedRepository.findByCat(cat)));
        return findFeeds;
    }

    public Page<Feed> findFeedByLike(long userId, int page, int size) {
        User findUser = userService.findUser(userId);
        PageRequest pageRequest = PageRequest.of(page, size, Sort.unsorted());
        Page<Like> likePage = likeService.findLikeByUserLike(pageRequest, findUser);
        return new PageImpl<Feed>(likePage.getContent().stream().map(Like::getFeed).collect(Collectors.toList()),
                likePage.getPageable(), likePage.getTotalElements());
    }
}
