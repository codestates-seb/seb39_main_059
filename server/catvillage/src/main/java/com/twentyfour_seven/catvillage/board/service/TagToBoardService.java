package com.twentyfour_seven.catvillage.board.service;

import com.twentyfour_seven.catvillage.board.dto.BoardTagDto;
import com.twentyfour_seven.catvillage.board.entity.Board;
import com.twentyfour_seven.catvillage.board.entity.BoardTag;
import com.twentyfour_seven.catvillage.board.entity.TagToBoard;
import com.twentyfour_seven.catvillage.board.entity.TagToBoardId;
import com.twentyfour_seven.catvillage.board.repository.BoardTagRepository;
import com.twentyfour_seven.catvillage.board.repository.TagToBoardRepository;
import com.twentyfour_seven.catvillage.exception.BusinessLogicException;
import com.twentyfour_seven.catvillage.exception.ExceptionCode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagToBoardService {

    private final TagToBoardRepository tagToBoardRepository;
    private final BoardTagService boardTagService;

    public TagToBoardService(TagToBoardRepository tagToBoardRepository, BoardTagService boardTagService) {
        this.tagToBoardRepository = tagToBoardRepository;
        this.boardTagService = boardTagService;
    }

    public List<TagToBoard> createTagToBoard(Board board, List<BoardTagDto> tags) {
        List<BoardTag> boardTags = boardTagService.getBoardTags(tags);
        return boardTags.stream().map(e -> {
            TagToBoard tagToBoard = TagToBoard.builder()
                    .board(board)
                    .boardTag(e)
                    .build();
            return tagToBoardRepository.save(tagToBoard);
        }).collect(Collectors.toList());
    }

    public void deleteTagToBoard(TagToBoard tag) {
        tagToBoardRepository.delete(tag);
    }

    public List<TagToBoard> updateTagToBoard(Board board, List<BoardTagDto> tags) {
        List<BoardTag> boardTags = boardTagService.getBoardTags(tags);

        // 기존에 존재하는 태그는 리스트에서 제거
        board.getTagToBoards().forEach(e -> boardTags.remove(e.getBoardTag()));

        // 추가된 태그만 저장 후 반환
        return boardTags.stream().map(e -> {
            TagToBoard tagToBoard = TagToBoard.builder()
                    .board(board)
                    .boardTag(e)
                    .build();
            return tagToBoardRepository.save(tagToBoard);
        }).collect(Collectors.toList());
    }
}
