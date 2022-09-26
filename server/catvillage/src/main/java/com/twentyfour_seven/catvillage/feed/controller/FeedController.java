package com.twentyfour_seven.catvillage.feed.controller;

import com.twentyfour_seven.catvillage.feed.mapper.FeedMapper;
import com.twentyfour_seven.catvillage.feed.service.FeedService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/냥이생활")
@Transactional
@Validated
public class FeedController {
    private final FeedService feedService;
    private final FeedMapper feedMapper;

    public FeedController(FeedService feedService, FeedMapper feedMapper) {
        this.feedService = feedService;
        this.feedMapper = feedMapper;
    }
}
