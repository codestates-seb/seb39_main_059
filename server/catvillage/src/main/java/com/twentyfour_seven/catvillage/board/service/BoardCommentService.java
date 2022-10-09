package com.twentyfour_seven.catvillage.board.service;

import com.twentyfour_seven.catvillage.board.entity.Board;
import com.twentyfour_seven.catvillage.board.entity.BoardComment;
import com.twentyfour_seven.catvillage.board.repository.BoardCommentRepository;
import com.twentyfour_seven.catvillage.common.like.LikeService;
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
    private LikeService likeService;

    public BoardCommentService(BoardCommentRepository boardCommentRepository, UserService userService,
                               CustomBeanUtils<BoardComment> beanUtils, LikeService likeService) {
        this.boardCommentRepository = boardCommentRepository;
        this.userService = userService;
        this.beanUtils = beanUtils;
        this.likeService = likeService;
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

    public void addLike(long commentId, User user) {
        // comment id 로 존재하는 게시글인지 확인하고 댓글 정보 가져옴
        BoardComment findComment = findVerifiedBoardComment(commentId);

        // like 추가
        likeService.addLikeInBoardComment(user, findComment);

        // comment 에 likeCount 1 올려서 저장
        findComment.setLikeCount(findComment.getLikeCount() + 1);
        boardCommentRepository.save(findComment);
    }

    public void deleteLike(long commentId, User user) {
        // comment id 로 존재하는 피드인지 확인하고 댓글 정보 가져옴
        BoardComment findComment = findVerifiedBoardComment(commentId);

        // like 삭제
        likeService.deleteLikeInBoardComment(user, findComment);

        // comment 에 likeCount 1 빼서 저장
        findComment.setLikeCount(findComment.getLikeCount() - 1);
        boardCommentRepository.save(findComment);
    }
}
