package com.twentyfour_seven.catvillage.board.service;

import com.twentyfour_seven.catvillage.board.entity.BoardComment;
import com.twentyfour_seven.catvillage.board.repository.BoardCommentRepository;
import com.twentyfour_seven.catvillage.utils.CustomBeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BoardCommentService {
    private BoardCommentRepository boardCommentRepository;
    private CustomBeanUtils<BoardComment> beanUtils;

    public BoardCommentService(BoardCommentRepository boardCommentRepository, CustomBeanUtils<BoardComment> beanUtils) {
        this.boardCommentRepository = boardCommentRepository;
        this.beanUtils = beanUtils;
    }
}
