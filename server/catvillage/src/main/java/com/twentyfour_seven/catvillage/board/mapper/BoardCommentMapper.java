package com.twentyfour_seven.catvillage.board.mapper;

import com.twentyfour_seven.catvillage.board.dto.BoardUserCommentResponseDto;
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
}
