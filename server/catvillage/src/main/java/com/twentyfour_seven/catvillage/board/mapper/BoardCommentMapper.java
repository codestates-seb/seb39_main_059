package com.twentyfour_seven.catvillage.board.mapper;

import com.twentyfour_seven.catvillage.board.dto.comment.BoardCommentPatchDto;
import com.twentyfour_seven.catvillage.board.dto.comment.BoardCommentPostDto;
import com.twentyfour_seven.catvillage.board.dto.comment.BoardCommentPostResponseDto;
import com.twentyfour_seven.catvillage.board.dto.comment.BoardUserCommentResponseDto;
import com.twentyfour_seven.catvillage.board.entity.Board;
import com.twentyfour_seven.catvillage.board.entity.BoardComment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BoardCommentMapper {
    default BoardUserCommentResponseDto boardCommentToBoardUserCommentResponseDto(BoardComment boardComment) {
        if(boardComment == null) {
            return null;
        } else {
            return BoardUserCommentResponseDto.builder()
                    .boardCommentId(boardComment.getBoardCommentId())
                    .boardTitle(boardComment.getBoard().getTitle())
                    .body(boardComment.getBody())
                    .likeCount(boardComment.getLikeCount())
                    .link("https://catvillage.tk/집사생활/" + boardComment.getBoard().getBoardId())
                    .createdDate(boardComment.getCreatedDate())
                    .build();
        }
    }

    List<BoardUserCommentResponseDto> boardCommentsToBoardUserCommentResponseDtos(List<BoardComment> boardComments);

    default BoardComment boardCommentPostDtoToBoardComment(BoardCommentPostDto requestBody) {
        if(requestBody == null) {
            return null;
        } else {
            return BoardComment.builder()
                    .board(Board.builder().boardId(requestBody.getBoardId()).build())
                    .body(requestBody.getBody())
                    .build();
        }
    }

    default BoardCommentPostResponseDto boardCommentToBoardCommentPostResponseDto(BoardComment boardComment) {
        if(boardComment == null) {
            return null;
        } else {
            return BoardCommentPostResponseDto.builder()
                    .boardCommentId(boardComment.getBoardCommentId())
                    .boardId(boardComment.getBoard().getBoardId())
                    .userId(boardComment.getUser().getUserId())
                    .body(boardComment.getBody())
                    .likeCount(boardComment.getLikeCount())
                    .createdDate(boardComment.getCreatedDate())
                    .modifiedDate(boardComment.getModifiedDate())
                    .build();
        }
    }

    default BoardComment boardCommentPatchDtoToBoardComment(BoardCommentPatchDto requestBody) {
        if(requestBody == null) {
            return null;
        } else {
            return BoardComment.builder()
                    .boardCommentId(requestBody.getBoardCommentId())
                    .body(requestBody.getBody())
                    .build();
        }
    }
}
