package com.twentyfour_seven.catvillage.board.controller;

import com.twentyfour_seven.catvillage.board.entity.BoardComment;
import com.twentyfour_seven.catvillage.board.mapper.BoardMapper;
import com.twentyfour_seven.catvillage.board.service.BoardCommentService;
import com.twentyfour_seven.catvillage.board.service.BoardService;
import com.twentyfour_seven.catvillage.board.service.BoardTagService;
import com.twentyfour_seven.catvillage.utils.CustomBeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/집사생활")
@Transactional
@Validated
public class BoardController {
    private BoardMapper boardMapper;
    private BoardService boardService;
    private BoardCommentService boardCommentService;
    private BoardTagService boardTagService;

    public BoardController(BoardMapper boardMapper, BoardService boardService, BoardCommentService boardCommentService, BoardTagService boardTagService) {
        this.boardMapper = boardMapper;
        this.boardService = boardService;
        this.boardCommentService = boardCommentService;
        this.boardTagService = boardTagService;
    }
}
