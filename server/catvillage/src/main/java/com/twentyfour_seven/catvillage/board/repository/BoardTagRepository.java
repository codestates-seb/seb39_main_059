package com.twentyfour_seven.catvillage.board.repository;

import com.twentyfour_seven.catvillage.board.entity.BoardTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardTagRepository extends JpaRepository<BoardTag, Long> {
    Optional<BoardTag> findByTagName(String tagName);
}
