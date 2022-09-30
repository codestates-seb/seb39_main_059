package com.twentyfour_seven.catvillage.board.controller;

import com.twentyfour_seven.catvillage.board.dto.BoardPatchDto;
import com.twentyfour_seven.catvillage.board.dto.BoardPostDto;
import com.twentyfour_seven.catvillage.board.entity.Board;
import com.twentyfour_seven.catvillage.board.mapper.BoardMapper;
import com.twentyfour_seven.catvillage.board.service.BoardCommentService;
import com.twentyfour_seven.catvillage.board.service.BoardService;
import com.twentyfour_seven.catvillage.user.entity.User;
import com.twentyfour_seven.catvillage.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
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

    @Operation(summary = "집사생활 새 글 작성하기",
            description = "집사생활에 등록되지 않은 태그로 요청이 들어올 경우 에러가 납니다.")
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

    @Operation(summary = "집사생활 글 수정하기",
            description = "집사생활에 등록되지 않은 태그로 요청이 들어올 경우 에러가 납니다. 처음에 글을 작성했던 유저와 로그인 되어 있는 유저가 다를 경우 405 에러가 납니다.")
    @PatchMapping("/{boards-id}")
    public ResponseEntity patchBoard(@AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
                                     @Positive @PathVariable("boards-id") Long boardId,
                                     @Valid @RequestBody BoardPatchDto requestBody) {
        Board board = boardMapper.boardPatchDtoToBoard(requestBody);
        User findUser = userService.findVerifiedEmail(user.getUsername());
        board.setUser(findUser);
        Board updateBoard = boardService.updateBoard(board, requestBody.getTag(), requestBody.getPicture());
        return new ResponseEntity<>(
                boardMapper.boardToBoardPostResponseDto(updateBoard),
                HttpStatus.OK);
    }

    @Operation(summary = "집사생활 글 삭제하기",
    description = "존재하지 않는 글의 식별자가 들어올 경우 에러가 납니다. 글 작성자가 아닌 다른 유저가 제거를 요청하면 405 에러가 납니다.")
    @DeleteMapping("/{board-id}")
    public ResponseEntity deleteBoard(@AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
                                      @Positive @PathVariable("board-id") Long boardId)
    {
        User findUser = userService.findVerifiedEmail(user.getUsername());
        boardService.deleteBoard(findUser, boardId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
