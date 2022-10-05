package com.twentyfour_seven.catvillage.user.repository;

import com.twentyfour_seven.catvillage.user.entity.Follow;
import com.twentyfour_seven.catvillage.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findByMember(User member);

    boolean existsByMemberAndTarget(User member, User target);

    Optional<Follow> findByMemberAndTarget(User member, User target);
}
