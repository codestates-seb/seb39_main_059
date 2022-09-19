package com.twentyfour_seven.catvillage.user.repository;

import com.twentyfour_seven.catvillage.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
