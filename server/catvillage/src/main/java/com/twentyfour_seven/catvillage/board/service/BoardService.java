package com.twentyfour_seven.catvillage.board.service;

import com.twentyfour_seven.catvillage.board.entity.Board;
import com.twentyfour_seven.catvillage.board.repository.BoardRepository;
import com.twentyfour_seven.catvillage.utils.CustomBeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
    private BoardRepository boardRepository;
    private CustomBeanUtils<Board> beanUtils;

    public BoardService(BoardRepository boardRepository, CustomBeanUtils<Board> beanUtils) {
        this.boardRepository = boardRepository;
        this.beanUtils = beanUtils;
    }
}
