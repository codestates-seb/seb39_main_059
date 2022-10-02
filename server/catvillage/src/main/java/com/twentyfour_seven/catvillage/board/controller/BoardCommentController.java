package com.twentyfour_seven.catvillage.board.controller;

import com.twentyfour_seven.catvillage.board.dto.comment.BoardCommentPatchDto;
import com.twentyfour_seven.catvillage.board.dto.comment.BoardCommentPostResponseDto;
import com.twentyfour_seven.catvillage.board.entity.BoardComment;
import com.twentyfour_seven.catvillage.board.mapper.BoardCommentMapper;
import com.twentyfour_seven.catvillage.board.service.BoardCommentService;
import com.twentyfour_seven.catvillage.utils.CustomBeanUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/집사생활/comments")
@Transactional
@Validated
public class BoardCommentController {
    private BoardCommentService boardCommentService;
    private BoardCommentMapper boardCommentMapper;
    private CustomBeanUtils<BoardComment> beanUtils;

    public BoardCommentController(BoardCommentService boardCommentService, BoardCommentMapper boardCommentMapper, CustomBeanUtils<BoardComment> beanUtils) {
        this.boardCommentService = boardCommentService;
        this.boardCommentMapper = boardCommentMapper;
        this.beanUtils = beanUtils;
    }

    @Operation(summary = "집사생활 댓글 조회",
        responses = {
            @ApiResponse(responseCode = "200", description = "집사생활 댓글 조회 성공", content = @Content(schema = @Schema(implementation = BoardCommentPostResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 집사생활 댓글")
        })
    @GetMapping("/{comments-id}")
    public ResponseEntity<?> getBoardComment(@Valid @PathVariable("comments-id") Long boardCommentId) {
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
    @PatchMapping("/{comments-id}")
    public ResponseEntity<?> patchBoardComment(@Valid @RequestBody BoardCommentPatchDto requestBody,
                                               @Valid @Positive @PathVariable("comments-id") Long boardCommentId,
                                               @AuthenticationPrincipal User user) {
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
    @DeleteMapping("/{comments-id}")
    public ResponseEntity<?> deleteBoardComment(@Valid @PathVariable("comments-id") Long boardCommentId,
                                                @AuthenticationPrincipal User user) {
        boardCommentService.deleteBoardComment(user.getUsername(), boardCommentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
