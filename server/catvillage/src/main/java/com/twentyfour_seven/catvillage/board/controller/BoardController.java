package com.twentyfour_seven.catvillage.board.controller;

import com.twentyfour_seven.catvillage.board.dto.BoardPostDto;
import com.twentyfour_seven.catvillage.board.entity.Board;
import com.twentyfour_seven.catvillage.board.mapper.BoardMapper;
import com.twentyfour_seven.catvillage.board.service.BoardCommentService;
import com.twentyfour_seven.catvillage.board.service.BoardService;
import com.twentyfour_seven.catvillage.user.entity.User;
import com.twentyfour_seven.catvillage.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/집사생활")
@Transactional
@Validated
public class BoardController {
    private BoardMapper boardMapper;
    private BoardService boardService;
    private UserService userService;
    private BoardCommentService boardCommentService;

    public BoardController(BoardMapper boardMapper, BoardService boardService, UserService userService, BoardCommentService boardCommentService) {
        this.boardMapper = boardMapper;
        this.boardService = boardService;
        this.userService = userService;
        this.boardCommentService = boardCommentService;
    }

    @PostMapping
    public ResponseEntity postBoard(@AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
                                    @Valid @RequestBody BoardPostDto requestBody) {
        // 집사생활의 태그는 제공되는 태그 중 선택하여 등록하는 태그이기 때문에 아래와 같은 로직은 불필요
//        List<BoardTag> boardTags = boardTagService.registerBoardTags(requestBody.getTag());
        Board board = boardMapper.boardPostDtoToBoard(requestBody);

        // 작성자 정보 획득
        User findUser = userService.findVerifiedEmail(user.getUsername());
        board.setUser(findUser);

        Board createdBoard = boardService.createBoard(board, requestBody.getTag(), requestBody.getPicture());
        return new ResponseEntity<>(
                boardMapper.boardToBoardPostResponseDto(createdBoard),
                HttpStatus.CREATED);
    }
}
