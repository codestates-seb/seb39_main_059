package com.twentyfour_seven.catvillage.user.controller;

import com.twentyfour_seven.catvillage.user.entity.Follow;
import com.twentyfour_seven.catvillage.user.entity.User;
import com.twentyfour_seven.catvillage.user.repository.FollowRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FollowService {
    private final FollowRepository followRepository;

    public FollowService(FollowRepository followRepository) {
        this.followRepository = followRepository;
    }

    // 냥이생활 전체 피드보기에서 특정 유저의 팔로워 목록 가져오는 로직
    public List<User> findFollowsInFeed(User user) {
        List<Follow> follows = followRepository.findByMember(user);
        if (follows == null) {
            return null;
        }
        List<User> targets = follows.stream().map(follow -> follow.getTarget()).collect(Collectors.toList());
        return targets;
    }
}
