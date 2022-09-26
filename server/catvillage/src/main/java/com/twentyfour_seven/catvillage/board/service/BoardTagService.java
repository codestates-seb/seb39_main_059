package com.twentyfour_seven.catvillage.board.service;

import com.twentyfour_seven.catvillage.board.repository.BoardTagRepository;
import org.springframework.stereotype.Service;

@Service
public class BoardTagService {
    private BoardTagRepository boardTagRepository;

    public BoardTagService(BoardTagRepository boardTagRepository) {
        this.boardTagRepository = boardTagRepository;
    }
}
