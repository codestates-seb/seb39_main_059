package com.twentyfour_seven.catvillage.feed.service;

import com.twentyfour_seven.catvillage.cat.entity.Cat;
import com.twentyfour_seven.catvillage.cat.service.CatService;
import com.twentyfour_seven.catvillage.common.picture.service.PictureService;
import com.twentyfour_seven.catvillage.exception.BusinessLogicException;
import com.twentyfour_seven.catvillage.exception.ExceptionCode;
import com.twentyfour_seven.catvillage.feed.entity.Feed;
import com.twentyfour_seven.catvillage.feed.repository.FeedRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FeedService {
    private final FeedRepository feedRepository;
    private final CatService catService;
    private final PictureService pictureService;

    public FeedService(FeedRepository feedRepository,
                       CatService catService,
                       PictureService pictureService) {
        this.feedRepository = feedRepository;
        this.catService = catService;
        this.pictureService = pictureService;
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
}
