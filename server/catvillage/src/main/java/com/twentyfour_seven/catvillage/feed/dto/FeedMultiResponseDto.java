package com.twentyfour_seven.catvillage.feed.dto;

import com.twentyfour_seven.catvillage.user.dto.follow.FollowFeedGetDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FeedMultiResponseDto {
    private List<FollowFeedGetDto> follow;
    private List<FeedMultiGetResponseDto> feed;
    private int page;
    private int pageSize;
    private int totalPages;
    private long totalElements;

    public FeedMultiResponseDto(List<FeedMultiGetResponseDto> feed, Page pageInfo) {
        this.feed = feed;
        this.page = pageInfo.getNumber() + 1;
        this.pageSize = pageInfo.getSize();
        this.totalPages = pageInfo.getTotalPages();
        this.totalElements = pageInfo.getTotalElements();
    }
}
