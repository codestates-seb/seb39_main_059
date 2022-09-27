package com.twentyfour_seven.catvillage.feed.mapper;

import com.twentyfour_seven.catvillage.common.picture.dto.PictureDto;
import com.twentyfour_seven.catvillage.common.picture.entity.Picture;
import com.twentyfour_seven.catvillage.feed.dto.FeedPostDto;
import com.twentyfour_seven.catvillage.feed.dto.FeedResponseDto;
import com.twentyfour_seven.catvillage.feed.entity.Feed;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
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
        feedPostDto.getPictures().forEach(
                pictureDto -> feed.getPictures().add(Picture.builder().path(pictureDto.getPicture()).build())
        );
        return feed;
    }
}
