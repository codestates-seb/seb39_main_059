package com.twentyfour_seven.catvillage.board.repository;

import com.twentyfour_seven.catvillage.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
