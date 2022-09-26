package com.twentyfour_seven.catvillage.board.service;

import com.twentyfour_seven.catvillage.board.repository.BoardRepository;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
    private BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }
}
