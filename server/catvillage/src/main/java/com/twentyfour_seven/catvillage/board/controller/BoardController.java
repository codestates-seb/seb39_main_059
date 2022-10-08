package com.twentyfour_seven.catvillage.board.controller;

import com.twentyfour_seven.catvillage.board.dto.*;
import com.twentyfour_seven.catvillage.board.dto.comment.BoardCommentPatchDto;
import com.twentyfour_seven.catvillage.board.dto.comment.BoardCommentPostDto;
import com.twentyfour_seven.catvillage.board.dto.comment.BoardCommentPostResponseDto;
import com.twentyfour_seven.catvillage.board.entity.Board;
import com.twentyfour_seven.catvillage.board.entity.BoardComment;
import com.twentyfour_seven.catvillage.board.mapper.BoardCommentMapper;
import com.twentyfour_seven.catvillage.board.mapper.BoardMapper;
import com.twentyfour_seven.catvillage.board.service.BoardCommentService;
import com.twentyfour_seven.catvillage.board.service.BoardService;
import com.twentyfour_seven.catvillage.dto.MultiBoardResponseDto;
import com.twentyfour_seven.catvillage.dto.MultiResponseDto;
import com.twentyfour_seven.catvillage.user.entity.User;
import com.twentyfour_seven.catvillage.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Positive;

//@Tag(name = "Board", description = "집사생활 API")
@RestController
@RequestMapping("/집사생활")
@Transactional
@Validated
public class BoardController {
    private BoardMapper boardMapper;
    private BoardCommentMapper boardCommentMapper;
    private BoardService boardService;
    private UserService userService;
    private BoardCommentService boardCommentService;

    public BoardController(BoardMapper boardMapper, BoardCommentMapper boardCommentMapper, BoardService boardService, UserService userService, BoardCommentService boardCommentService) {
        this.boardMapper = boardMapper;
        this.boardCommentMapper = boardCommentMapper;
        this.boardService = boardService;
        this.userService = userService;
        this.boardCommentService = boardCommentService;
    }

    @Operation(summary = "집사생활 전체 게시글 보기",
            responses = {
                    @ApiResponse(responseCode = "200", description = "집사생활 전체 게시글 조회 성공", content = @Content(schema = @Schema(implementation = MultiBoardResponseDto.class)))
            })
    @GetMapping
    public ResponseEntity getBoards(@RequestParam @Positive int page,
                                    @RequestParam @Positive int size) {
        Page<Board> boards = boardService.findBoards(page - 1, size);
        return new ResponseEntity<>(
                new MultiResponseDto<BoardMultiGetResponse>(
                        boardMapper.boardsToBoardMultiGetResponseDtos(boards.getContent()),
                        boards
                ),
                HttpStatus.OK);
    }

    @Operation(summary = "집사생활 특정 게시글 보기",
            responses = {
                    @ApiResponse(responseCode = "200", description = "집사생활 게시글 조회 성공", content = @Content(schema = @Schema(implementation = BoardGetResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글")
            })
    @GetMapping("/{boards-id}")
    public ResponseEntity getBoard(@Positive @PathVariable("boards-id") Long boardId) {
        Board board = boardService.findBoard(boardId);
        BoardGetResponseDto responseDto = boardMapper.boardToBoardGetResponseDto(board);
        responseDto.setComments(boardCommentMapper.boardCommentsToBoardCommentResponseDtos(board.getBoardComments()));
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "집사생활 새 글 작성하기",
            description = "집사생활에 등록되지 않은 태그로 요청이 들어올 경우 에러가 납니다.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "집사생활 게시글 등록 성공", content = @Content(schema = @Schema(implementation = BoardPostResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 유저 정보\n존재하지 않는 태그")
            })
    @PostMapping
    public ResponseEntity postBoard(@AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
                                    @Valid @RequestBody BoardPostDto requestBody) {
        // 집사생활의 태그는 제공되는 태그 중 선택하여 등록하는 태그이기 때문에 아래와 같은 로직은 불필요
//        List<BoardTag> boardTags = boardTagService.registerBoardTags(requestBody.getTag());
        Board board = boardMapper.boardPostDtoToBoard(requestBody);

        // 작성자 정보 획득
        User findUser = userService.findVerifiedEmail(user.getUsername());
        board.setUser(findUser);

        Board createdBoard = boardService.createBoard(board, requestBody.getTags(), requestBody.getPictures());

        // 유저 contentCount + 1하여 저장
        userService.addContentCount(findUser);

        return new ResponseEntity<>(
                boardMapper.boardToBoardPostResponseDto(createdBoard),
                HttpStatus.CREATED);
    }

    @Operation(summary = "집사생활 글 수정하기",
            description = "집사생활에 등록되지 않은 태그로 요청이 들어올 경우 에러가 납니다. 처음에 글을 작성했던 유저와 로그인 되어 있는 유저가 다를 경우 405 에러가 납니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "집사생활 게시글 수정 성공", content = @Content(schema = @Schema(implementation = BoardPostResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 유저 정보\n존재하지 않는 게시글"),
                    @ApiResponse(responseCode = "405", description = "유저 정보 불일치")
            })
    @PatchMapping("/{boards-id}")
    public ResponseEntity patchBoard(@AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
                                     @Positive @PathVariable("boards-id") Long boardId,
                                     @Valid @RequestBody BoardPatchDto requestBody) {
        requestBody.setBoardId(boardId);
        Board board = boardMapper.boardPatchDtoToBoard(requestBody);
        User findUser = userService.findVerifiedEmail(user.getUsername());
        board.setUser(findUser);
        Board updateBoard = boardService.updateBoard(board, requestBody.getTags(), requestBody.getPictures());
        return new ResponseEntity<>(
                boardMapper.boardToBoardPostResponseDto(updateBoard),
                HttpStatus.OK);
    }

    @Operation(summary = "집사생활 글 삭제하기",
            description = "존재하지 않는 글의 식별자가 들어올 경우 에러가 납니다. 글 작성자가 아닌 다른 유저가 제거를 요청하면 405 에러가 납니다.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "집사생활 게시글 삭제 성공"),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글"),
                    @ApiResponse(responseCode = "405", description = "유저 정보 불일치")
            })
    @DeleteMapping("/{board-id}")
    public ResponseEntity deleteBoard(@AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
                                      @Positive @PathVariable("board-id") Long boardId) {
        User findUser = userService.findVerifiedEmail(user.getUsername());
        boardService.deleteBoard(findUser, boardId);

        // 유저 contentCount - 1하여 저장
        userService.removeContentCount(findUser);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "집사생활 댓글 작성",
            responses = {
                    @ApiResponse(responseCode = "200", description = "집사생활 댓글 작성 성공", content = @Content(schema = @Schema(implementation = BoardCommentPostResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 유저")
            })
    @PostMapping("/{boards-id}/comments")
    public ResponseEntity<?> postBoardComment(@AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
                                              @Positive @PathVariable("boards-id") Long boardId,
                                              @Valid @RequestBody BoardCommentPostDto requestBody) {
        requestBody.setBoardId(boardId);
        BoardComment boardComment = boardCommentMapper.boardCommentPostDtoToBoardComment(requestBody);
        BoardComment saveComment = boardCommentService.createBoardComment(user.getUsername(), boardComment);
        return new ResponseEntity<>(boardCommentMapper.boardCommentToBoardCommentPostResponseDto(saveComment), HttpStatus.CREATED);
    }

    @Operation(summary = "집사생활 댓글 조회",
            responses = {
                    @ApiResponse(responseCode = "200", description = "집사생활 댓글 조회 성공", content = @Content(schema = @Schema(implementation = BoardCommentPostResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 집사생활 댓글")
            })
    @GetMapping("/comments/{comments-id}")
    public ResponseEntity<?> getBoardComment(@Positive @PathVariable("comments-id") Long boardCommentId) {
        BoardComment findBoardComment = boardCommentService.findBoardComment(boardCommentId);
        return new ResponseEntity<>(
                boardCommentMapper.boardCommentToBoardCommentPostResponseDto(findBoardComment),
                HttpStatus.OK);
    }

    @Operation(summary = "집사생활 댓글 수정",
            responses = {
                    @ApiResponse(responseCode = "200", description = "집사생활 댓글 수정 성공", content = @Content(schema = @Schema(implementation = BoardCommentPostResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 집사생활 댓글\n존재하지 않는 유저"),
                    @ApiResponse(responseCode = "405", description = "유저 정보 불일치")
            })
    @PatchMapping("/comments/{comments-id}")
    public ResponseEntity<?> patchBoardComment(@Valid @RequestBody BoardCommentPatchDto requestBody,
                                               @Positive @PathVariable("comments-id") Long boardCommentId,
                                               @AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
        requestBody.setBoardCommentId(boardCommentId);
        BoardComment boardComment = boardCommentMapper.boardCommentPatchDtoToBoardComment(requestBody);
        BoardComment updateBoardComment = boardCommentService.updateBoardComment(user.getUsername(), boardComment);
        return new ResponseEntity<>(
                boardCommentMapper.boardCommentToBoardCommentPostResponseDto(updateBoardComment),
                HttpStatus.OK);
    }

    @Operation(summary = "집사생활 댓글 삭제",
            responses = {
                    @ApiResponse(responseCode = "204", description = "집사생활 댓글 삭제 성공"),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 집사생활 댓글\n존재하지 않는 유저"),
                    @ApiResponse(responseCode = "405", description = "유저 정보 불일치")
            })
    @DeleteMapping("/comments/{comments-id}")
    public ResponseEntity<?> deleteBoardComment(@Positive @PathVariable("comments-id") Long boardCommentId,
                                                @AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
        boardCommentService.deleteBoardComment(user.getUsername(), boardCommentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "집사생활 게시글에 좋아요 추가", description = "로그인한 유저 정보를 가져와서 해당 게시글에 좋아요를 추가합니다.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "좋아요 등록 성공"),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글"),
                    @ApiResponse(responseCode = "409", description = "이미 좋아요가 존재")
            })
    @PostMapping("/{boards-id}/like")
    public ResponseEntity postLikeInBoard(@PathVariable("boards-id") @Positive long boardId,
                                          @AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
        User findUser = userService.findVerifiedEmail(user.getUsername());
        boardService.addLike(boardId, findUser);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "집사생활 게시글에 좋아요 삭제", description = "로그인한 유저 정보를 가져와서 해당 피드에 좋아요를 삭제합니다.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "좋아요 삭제 성공"),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글"),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 좋아요")
            })
    @DeleteMapping("/{boards-id}/like")
    public ResponseEntity deleteLikeInFeed(@PathVariable("boards-id") @Positive long boardId,
                                           @AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
        User findUser = userService.findVerifiedEmail(user.getUsername());
        boardService.deleteLike(boardId, findUser);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "집사생활 댓글에 좋아요 추가", description = "로그인한 유저 정보를 가져와서 해당 댓글에 좋아요를 추가합니다.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "좋아요 등록 성공"),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 댓글"),
                    @ApiResponse(responseCode = "409", description = "이미 좋아요가 존재")
            })
    @PostMapping("/comments/{comments-id}/like")
    public ResponseEntity postLikeInComment(@PathVariable("comments-id") @Positive long commentId,
                                          @AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
        User findUser = userService.findVerifiedEmail(user.getUsername());
        boardCommentService.addLike(commentId, findUser);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "집사생활 댓글에 좋아요 삭제", description = "로그인한 유저 정보를 가져와서 해당 댓글에 좋아요를 삭제합니다.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "좋아요 삭제 성공"),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 댓글"),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 좋아요")
            })
    @DeleteMapping("/comments/{comments-id}/like")
    public ResponseEntity deleteLikeInComment(@PathVariable("comments-id") @Positive long commentId,
                                           @AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
        User findUser = userService.findVerifiedEmail(user.getUsername());
        boardCommentService.deleteLike(commentId, findUser);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
