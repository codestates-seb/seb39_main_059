package com.twentyfour_seven.catvillage.user.service;

import com.twentyfour_seven.catvillage.exception.BusinessLogicException;
import com.twentyfour_seven.catvillage.exception.ExceptionCode;
import com.twentyfour_seven.catvillage.user.entity.Follow;
import com.twentyfour_seven.catvillage.user.entity.User;
import com.twentyfour_seven.catvillage.user.repository.FollowRepository;
import com.twentyfour_seven.catvillage.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FollowService {
    private final FollowRepository followRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public FollowService(FollowRepository followRepository, UserService userService, UserRepository userRepository) {
        this.followRepository = followRepository;
        this.userService = userService;
        this.userRepository = userRepository;
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

    // Following : 내가 타겟을 팔로우
    // Follower : 타겟이 나를 팔로우

    public Follow addFollowing(String email, Long targetId) {
        User user = userService.findVerifiedEmail(email);
        User targetUser = userService.findUser(targetId);

        // 이미 팔로우 중인지 검증, 있으면 예외 발생
        verifiedExistsFollowing(user, targetUser);

        Follow follow = followRepository.save(Follow.builder()
                .member(user)
                .target(targetUser)
                .build());

        // User following update
        user.getFollowing().add(follow);
        user.setFollowingCount(user.getFollowingCount() + 1);
        userRepository.save(user);

        // Target follower update
        targetUser.getFollower().add(follow);
        targetUser.setFollowerCount(targetUser.getFollowerCount() + 1);
        userRepository.save(targetUser);

        return follow;
    }

    public void deleteFollowing(String email, Long targetId) {
        User user = userService.findVerifiedEmail(email);
        User targetUser = userService.findUser(targetId);

        // 팔로우 중인지 검증, 없으면 예외 발생
        Follow findFollow = findVerifiedFollowing(user, targetUser);

        // User following update
        user.getFollowing().remove(findFollow);
        user.setFollowingCount(user.getFollowingCount() - 1);
        userRepository.save(user);

        // Target follower update
        targetUser.getFollower().remove(findFollow);
        targetUser.setFollowerCount(targetUser.getFollowerCount() - 1);
        userRepository.save(targetUser);

        followRepository.delete(findFollow);
    }

    private void verifiedExistsFollowing(User user, User targetUser) {
        if (followRepository.existsByMemberAndTarget(user, targetUser)) {
            throw new BusinessLogicException(ExceptionCode.FOLLOWER_EXISTS);
        }
    }

    private Follow findVerifiedFollowing(User user, User targetUser) {
        Optional<Follow> findFollow = followRepository.findByMemberAndTarget(user, targetUser);
        return findFollow.orElseThrow(() -> new BusinessLogicException(ExceptionCode.FOLLOWER_NOT_FOUND));
    }
}
