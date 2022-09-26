package com.twentyfour_seven.catvillage.board.controller;

import com.twentyfour_seven.catvillage.board.mapper.BoardMapper;
import com.twentyfour_seven.catvillage.board.service.BoardService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/집사생활")
public class BoardController {
    private BoardMapper boardMapper;
    private BoardService boardService;

    public BoardController(BoardMapper boardMapper, BoardService boardService) {
        this.boardMapper = boardMapper;
        this.boardService = boardService;
    }
}
