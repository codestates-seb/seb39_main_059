package com.twentyfour_seven.catvillage.board.repository;

import com.twentyfour_seven.catvillage.board.entity.BoardComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardCommentRepository extends JpaRepository<BoardComment, Long> {
}
