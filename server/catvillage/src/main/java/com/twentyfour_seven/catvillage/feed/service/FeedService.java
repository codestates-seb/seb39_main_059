package com.twentyfour_seven.catvillage.feed.service;

import com.twentyfour_seven.catvillage.cat.entity.Cat;
import com.twentyfour_seven.catvillage.cat.service.CatService;
import com.twentyfour_seven.catvillage.common.picture.entity.Picture;
import com.twentyfour_seven.catvillage.common.picture.service.PictureService;
import com.twentyfour_seven.catvillage.exception.BusinessLogicException;
import com.twentyfour_seven.catvillage.exception.ExceptionCode;
import com.twentyfour_seven.catvillage.feed.entity.Feed;
import com.twentyfour_seven.catvillage.feed.repository.FeedRepository;
import com.twentyfour_seven.catvillage.user.controller.FollowService;
import com.twentyfour_seven.catvillage.user.entity.User;
import com.twentyfour_seven.catvillage.user.repository.UserRepository;
import com.twentyfour_seven.catvillage.user.service.UserService;
import com.twentyfour_seven.catvillage.utils.CustomBeanUtils;
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

    public Feed createFeed(Feed feed, long catId, String email) {
        // 작성한 고양이 프로필이 유효한지 확인
        Cat findCat = catService.findVerifiedCat(catId);

        // 로그인한 유저의 고양이가 아닐경우 에러
        if (!findCat.getUser().getEmail().equals(email)) {
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

    public Feed updateFeed(long feedId, Feed feed, String email) {
        // feedId로 저장되있던 feed 정보를 불러옴
        Feed findFeed = findVerifiedFeed(feedId);

        // 글을 처음 작성한 유저와 로그인한 유저가 동일한지 확인하고 아니면 Exception throw
        if (!findFeed.getCat().getUser().getEmail().equals(email)) {
            throw new BusinessLogicException(ExceptionCode.INVALID_USER);
        }

        // Feed 내용 업데이트
        Feed updateFeed = findFeed;
        if (findFeed.getBody() != feed.getBody()) {
            updateFeed.setBody(feed.getBody());
        }

        // Picture 업데이트를 위해 source, definition의 path만 따로 저장
        List<String> picturePaths1 = new ArrayList<>();
        List<String> picturePaths2 = new ArrayList<>();
        feed.getPictures().forEach(
                picture -> {
                    picturePaths1.add(picture.getPath());
                }
        );
        updateFeed.getPictures().forEach(
                picture -> {
                    picturePaths2.add(picture.getPath());
                }
        );
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
                        picture = pictureService.createPicture(picture);
                        findFeed.getPictures().add(picture);
                    }
                }
        );
        return feedRepository.save(updateFeed);
    }

    public void removeFeed(long feedId, String email) {
        Feed findFeed = findVerifiedFeed(feedId);
        User findUser = userService.findVerifiedEmail(email);
        // feed에 저장된 유저와 요청을 보낸 유저가 다르면 INVALID_USER Exception throw
        if (!findFeed.getCat().getUser().getUserId().equals(findUser.getUserId())) {
            throw new BusinessLogicException(ExceptionCode.INVALID_USER);
        }
        feedRepository.delete(findFeed);
    }
}
