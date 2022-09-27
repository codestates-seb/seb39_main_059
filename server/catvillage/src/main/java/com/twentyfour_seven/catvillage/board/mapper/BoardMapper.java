package com.twentyfour_seven.catvillage.board.mapper;

import com.twentyfour_seven.catvillage.board.dto.BoardPostDto;
import com.twentyfour_seven.catvillage.board.dto.BoardPostResponseDto;
import com.twentyfour_seven.catvillage.board.dto.BoardTagDto;
import com.twentyfour_seven.catvillage.board.entity.Board;
import com.twentyfour_seven.catvillage.common.picture.dto.PictureDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BoardMapper {
    default Board boardPostDtoToBoard(BoardPostDto requestBody) {
        if(requestBody == null) {
            return null;
        } else {
            return Board.builder()
                .title(requestBody.getTitle())
                .body(requestBody.getBody())
                .build();
        }
    }

    default BoardPostResponseDto boardToBoardPostResponseDto(Board board) {
        if(board == null) {
            return null;
        } else {
            BoardPostResponseDto response = BoardPostResponseDto.builder()
                    .boardId(board.getBoardId())
                    .title(board.getTitle())
                    .body(board.getBody())
                    .build();
            // Tag name 등록
            board.getTagToBoards().forEach(e -> {
                response.getTag().add(new BoardTagDto(e.getBoardTag()));
            });
            // Picture path 등록
            board.getPictures().forEach(e -> {
                response.getPicture().add(new PictureDto(e));
            });
            return response;
        }
    }
}
