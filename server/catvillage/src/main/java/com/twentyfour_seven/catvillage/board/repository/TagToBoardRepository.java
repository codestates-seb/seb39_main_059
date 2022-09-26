package com.twentyfour_seven.catvillage.board.repository;

import com.twentyfour_seven.catvillage.board.entity.TagToBoard;
import com.twentyfour_seven.catvillage.board.entity.TagToBoardId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagToBoardRepository extends JpaRepository<TagToBoard, TagToBoardId> {
}
