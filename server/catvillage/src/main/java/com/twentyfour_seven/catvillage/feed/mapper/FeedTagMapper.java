package com.twentyfour_seven.catvillage.feed.mapper;

import com.twentyfour_seven.catvillage.feed.dto.FeedTagDto;
import com.twentyfour_seven.catvillage.feed.entity.FeedTag;
import com.twentyfour_seven.catvillage.feed.entity.TagToFeed;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FeedTagMapper {
    default FeedTag tagToFeedToFeedTag (TagToFeed tagToFeed) {
        if (tagToFeed == null) {
            return null;
        }
        return new FeedTag(tagToFeed.getFeedTag().getTagName());
    }

    List<FeedTag> tagToFeedsToFeedTags (List<TagToFeed> tagToFeeds);

    default FeedTag feedTagDtoToFeedTag (FeedTagDto feedTagDto) {
        if (feedTagDto == null) {
            return null;
        }
        return new FeedTag(feedTagDto.getTag());
    }

    List<FeedTag> feedTagDtosToFeedTags (List<FeedTagDto> feedTagDtos);
}
