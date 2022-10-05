package com.twentyfour_seven.catvillage.feed.mapper;

import com.twentyfour_seven.catvillage.common.picture.dto.PictureDto;
import com.twentyfour_seven.catvillage.common.picture.entity.Picture;
import com.twentyfour_seven.catvillage.feed.dto.FeedGetResponseDto;
import com.twentyfour_seven.catvillage.feed.dto.FeedMultiGetResponseDto;
import com.twentyfour_seven.catvillage.feed.dto.FeedPostDto;
import com.twentyfour_seven.catvillage.feed.dto.FeedResponseDto;
import com.twentyfour_seven.catvillage.feed.entity.Feed;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FeedMapper {
    default FeedResponseDto feedToFeedResponseDto(Feed feed) {
        if (feed == null) {
            return null;
        }
        List<PictureDto> pictureDtos = new ArrayList<>();
        feed.getPictures().forEach(
                picture -> pictureDtos.add(new PictureDto(picture))
        );
        return new FeedResponseDto(
                feed.getFeedId(),
                feed.getCat().getCatId(),
                pictureDtos,
                feed.getBody()
        );
    }

    default Feed feedPostDtoToFeed(FeedPostDto feedPostDto) {
        if (feedPostDto == null) {
            return null;
        }
        Feed feed = new Feed(feedPostDto.getBody());
        if (feedPostDto.getPictures() != null) {
            feedPostDto.getPictures().forEach(
                    pictureDto -> feed.getPictures().add(Picture.builder().path(pictureDto.getPicture()).build())
            );
        }
        return feed;
    }

    default FeedGetResponseDto feedToFeedGetResponseDto(Feed feed) {
        if (feed == null) {
            return null;
        }
        return FeedGetResponseDto.builder()
                .feedId(feed.getFeedId())
                .catId(feed.getCat().getCatId())
                .name(feed.getCat().getName())
                .profileImage(feed.getCat().getImage())
                .body(feed.getBody())
                .likeCount(feed.getLikeCount())
                .build();
    }

    List<FeedGetResponseDto> feedsToFeedGetResponseDtos(List<Feed> feeds);

    default FeedMultiGetResponseDto feedToFeedMultiGetResponseDto(Feed feed, String email) {
        FeedMultiGetResponseDto feedMultiGetResponseDto =  new FeedMultiGetResponseDto();
        feedMultiGetResponseDto.setFeedId(feed.getFeedId());

        // 사진 리스트에서 첫번쨰 사진 가져와서 대표사진으로 저장
        if(!feed.getPictures().isEmpty()) {
            feedMultiGetResponseDto.setImage(feed.getPictures().get(0).getPath());
        }

        // 피드마다 좋아요 여부 저장
        boolean isLike = false;
        if (email != null) {
            isLike = feed.getLikes().stream().anyMatch(like -> like.getUser().getEmail().equals(email));
        }
        feedMultiGetResponseDto.setIsLike(isLike);

        return feedMultiGetResponseDto;
    }
    default List<FeedMultiGetResponseDto> feedsToFeedMultiGetResponseDtos(List<Feed> feeds, String email) {
        if (feeds == null) {
            return null;
        }

        List<FeedMultiGetResponseDto> list = new ArrayList<>();

        for (Feed feed : feeds) {
            list.add(feedToFeedMultiGetResponseDto(feed, email));
        }

        return list;
    }
}
