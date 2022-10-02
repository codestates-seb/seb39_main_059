package com.twentyfour_seven.catvillage.board.service;

import com.twentyfour_seven.catvillage.board.entity.BoardComment;
import com.twentyfour_seven.catvillage.board.repository.BoardCommentRepository;
import com.twentyfour_seven.catvillage.exception.BusinessLogicException;
import com.twentyfour_seven.catvillage.exception.ExceptionCode;
import com.twentyfour_seven.catvillage.user.entity.User;
import com.twentyfour_seven.catvillage.user.service.UserService;
import com.twentyfour_seven.catvillage.utils.CustomBeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BoardCommentService {
    private BoardCommentRepository boardCommentRepository;
    private UserService userService;
    private CustomBeanUtils<BoardComment> beanUtils;

    public BoardCommentService(BoardCommentRepository boardCommentRepository, UserService userService, CustomBeanUtils<BoardComment> beanUtils) {
        this.boardCommentRepository = boardCommentRepository;
        this.userService = userService;
        this.beanUtils = beanUtils;
    }

    public BoardComment createBoardComment(String email, BoardComment boardComment) {
        User findUser = userService.findVerifiedEmail(email);
        boardComment.setUser(findUser);
        return boardCommentRepository.save(boardComment);
    }

    public BoardComment updateBoardComment(String email, BoardComment boardComment) {
        User findUser = userService.findVerifiedEmail(email);
        BoardComment findComment = boardCommentRepository.findById((boardComment.getBoardCommentId()))
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.BOARD_COMMENT_NOT_FOUND));

        // 유저 검증
        if(!findComment.getUser().getUserId().equals(findUser.getUserId())) {
            throw new BusinessLogicException(ExceptionCode.INVALID_USER);
        }

        BoardComment updateBoardComment = beanUtils.copyNonNullProperties(boardComment, findComment);
        return boardCommentRepository.save(updateBoardComment);
    }

    public BoardComment findBoardComment(Long boardCommentId) {
        return findVerifiedBoardComment(boardCommentId);
    }

    public void deleteBoardComment(String email, Long boardCommentId) {
        User findUser = userService.findVerifiedEmail(email);
        BoardComment findBoardComment = findVerifiedBoardComment(boardCommentId);

        // 유저 검증
        if(!findBoardComment.getUser().getUserId().equals(findUser.getUserId())) {
            throw new BusinessLogicException(ExceptionCode.INVALID_USER);
        }

        boardCommentRepository.delete(findBoardComment);
    }

    private BoardComment findVerifiedBoardComment(Long boardCommentId) {
        Optional<BoardComment> findBoardComment = boardCommentRepository.findById(boardCommentId);
        return findBoardComment.orElseThrow(() -> new BusinessLogicException(ExceptionCode.BOARD_COMMENT_NOT_FOUND));
    }
}
