package com.twentyfour_seven.catvillage.board.repository;

import com.twentyfour_seven.catvillage.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Page<Board> findByTitleIsContaining(String keyword, Pageable pageable);

    Page<Board> findByBodyIsContaining(String keyword, Pageable pageable);

    Page<Board> findByUserNameIsContaining(String keyword, Pageable pageable);
//    Page<Board> findByTitleIsContainingOrBodyIsContainingOrUserNameIsContaining(String keyword, Pageable pageable);
}
