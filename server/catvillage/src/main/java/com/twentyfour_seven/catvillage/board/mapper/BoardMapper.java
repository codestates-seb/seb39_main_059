package com.twentyfour_seven.catvillage.board.mapper;

import com.twentyfour_seven.catvillage.board.dto.*;
import com.twentyfour_seven.catvillage.board.entity.Board;
import com.twentyfour_seven.catvillage.common.picture.dto.PictureDto;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    default Board boardPatchDtoToBoard(BoardPatchDto requestBody) {
        if(requestBody == null) {
            return null;
        } else {
            return Board.builder()
                    .boardId(requestBody.getBoardId())
                    .title(requestBody.getTitle())
                    .body(requestBody.getBody())
                    .build();
        }
    }

    default BoardMultiGetResponse boardToBoardMultiGetResponseDto(Board board) {
        if(board == null) {
            return null;
        } else {
            return BoardMultiGetResponse.builder()
                    .boardId(board.getBoardId())
                    .title(board.getTitle())
                    .name(board.getUser().getName())
                    .tags(board.getTagToBoards().stream()
                            .map(e -> new BoardTagDto(e.getBoardTag()))
                            .collect(Collectors.toList()))
                    .picture(board.getPictures().isEmpty() ? "" : board.getPictures().get(0).getPath())
                    .viewCount(board.getViewCount())
                    .likeCount(board.getLikeCount())
                    .commentCount(board.getCommentCount())
                    .createdDate(board.getCreatedDate())
                    .build();
        }
    }

    List<BoardMultiGetResponse> boardsToBoardMultiGetResponseDtos(List<Board> boards);

    default BoardGetResponseDto boardToBoardGetResponseDto(Board board) {
        if(board == null) {
            return null;
        } else {
            return BoardGetResponseDto.builder()
                    .boardId(board.getBoardId())
                    .title(board.getTitle())
                    .body(board.getBody())
                    .name(board.getUser().getName())
                    .tags(board.getTagToBoards().stream()
                            .map(e -> new BoardTagDto(e.getBoardTag()))
                            .collect(Collectors.toList()))
                    .pictures(board.getPictures().isEmpty() ? new ArrayList<>() :
                            board.getPictures().stream()
                                    .map(PictureDto::new)
                                    .collect(Collectors.toList()))
                    .viewCount(board.getViewCount())
                    .likeCount(board.getLikeCount())
                    .commentCount(board.getCommentCount())
                    .createdDate(board.getCreatedDate())
                    .isLike(false)  // TODO: Like 기능 추가 후 수정 필요
                    .build();
        }
    }
}
