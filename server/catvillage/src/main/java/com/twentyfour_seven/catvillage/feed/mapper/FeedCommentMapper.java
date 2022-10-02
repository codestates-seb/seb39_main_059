package com.twentyfour_seven.catvillage.feed.mapper;

import com.twentyfour_seven.catvillage.cat.entity.Cat;
import com.twentyfour_seven.catvillage.feed.dto.FeedCommentGetDto;
import com.twentyfour_seven.catvillage.feed.dto.FeedCommentPostDto;
import com.twentyfour_seven.catvillage.feed.entity.Feed;
import com.twentyfour_seven.catvillage.feed.entity.FeedComment;
import com.twentyfour_seven.catvillage.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FeedCommentMapper {
    default FeedCommentGetDto feedCommentToFeedCommentGetDto(FeedComment feedComment) {
        if (feedComment == null) {
            return null;
        }
        if (feedComment.getCat() == null) {
            return FeedCommentGetDto.builder()
                    .feedCommentId(feedComment.getFeedCommentId())
                    .feedId(feedComment.getFeed().getFeedId())
                    .userId(feedComment.getUser().getUserId())
                    .catId(null)
                    .body(feedComment.getBody())
                    .likeCount(feedComment.getLikeCount()).build();
        } else {
            return FeedCommentGetDto.builder()
                    .feedCommentId(feedComment.getFeedCommentId())
                    .feedId(feedComment.getFeed().getFeedId())
                    .userId(null)
                    .catId(feedComment.getCat().getCatId())
                    .body(feedComment.getBody())
                    .likeCount(feedComment.getLikeCount()).build();
        }
    }

    List<FeedCommentGetDto> feedCommentsToFeedCommentGetDtos(List<FeedComment> feedComments);

    default FeedComment feedCommentPostDtoToFeedComment(long feedId, FeedCommentPostDto feedCommentPostDto) {
        if (feedCommentPostDto == null) {
            return null;
        }

        FeedComment feedComment = new FeedComment(feedCommentPostDto.getBody());
        feedComment.setFeed(new Feed(feedId));

        if (feedCommentPostDto.getIsCat()) {
            feedComment.setCat(new Cat(feedCommentPostDto.getProfileId()));
            feedComment.setUser(null);
        } else {
            feedComment.setUser(new User(feedCommentPostDto.getProfileId()));
            feedComment.setCat(null);
        }
        return feedComment;
    }
}
