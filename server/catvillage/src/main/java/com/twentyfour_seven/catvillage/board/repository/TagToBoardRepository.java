package com.twentyfour_seven.catvillage.board.repository;

import com.twentyfour_seven.catvillage.board.entity.Board;
import com.twentyfour_seven.catvillage.board.entity.BoardTag;
import com.twentyfour_seven.catvillage.board.entity.TagToBoard;
import com.twentyfour_seven.catvillage.board.entity.TagToBoardId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagToBoardRepository extends JpaRepository<TagToBoard, TagToBoardId> {
    Optional<TagToBoard> findByBoardTag(BoardTag boardTag);
}
