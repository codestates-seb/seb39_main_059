package com.twentyfour_seven.catvillage.feed.mapper;

import com.twentyfour_seven.catvillage.feed.dto.FeedCommentGetDto;
import com.twentyfour_seven.catvillage.feed.entity.FeedComment;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FeedCommentMapper {
    default FeedCommentGetDto feedCommentToFeedCommentGetDtos(FeedComment feedComment) {
        if (feedComment == null) {
            return null;
        }
        return new FeedCommentGetDto(
                feedComment.getFeedCommentId(),
                feedComment.getUser().getUserId(),
                feedComment.getCat().getCatId(),
                feedComment.getBody(),
                feedComment.getLikeCount()
        );
    }

    List<FeedCommentGetDto> feedCommentsToFeedCommentGetDtos(List<FeedComment> feedComments);
}
