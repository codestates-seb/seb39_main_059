package com.twentyfour_seven.catvillage.board.service;

import com.twentyfour_seven.catvillage.board.entity.Board;
import com.twentyfour_seven.catvillage.board.entity.TagToBoard;
import com.twentyfour_seven.catvillage.board.repository.BoardRepository;
import com.twentyfour_seven.catvillage.board.repository.BoardTagRepository;
import com.twentyfour_seven.catvillage.common.picture.entity.Picture;
import com.twentyfour_seven.catvillage.common.picture.service.PictureService;
import com.twentyfour_seven.catvillage.utils.CustomBeanUtils;
import com.twentyfour_seven.catvillage.exception.BusinessLogicException;
import com.twentyfour_seven.catvillage.exception.ExceptionCode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BoardService {
    private BoardRepository boardRepository;
    private TagToBoardService tagToBoardService;
    private PictureService pictureService;
    private CustomBeanUtils<Board> beanUtils;

    public BoardService(BoardRepository boardRepository, TagToBoardService tagToBoardService, PictureService pictureService, CustomBeanUtils<Board> beanUtils) {
        this.boardRepository = boardRepository;
        this.tagToBoardService = tagToBoardService;
        this.pictureService = pictureService;
        this.beanUtils = beanUtils;
    }

    public Board createBoard(Board board, List<String> tags, List<String> pictures) {
        // ID를 얻기 위한 저장 로직
        Board createdBoard = boardRepository.save(board);

        // TagToBoard 리스트를 생성하여 DB에 저장 및 반환
        List<TagToBoard> tagToBoard = tagToBoardService.createTagToBoard(createdBoard, tags);
        // createdBoard 객체의 TagToBoard 필드에 추가
        createdBoard.getTagToBoards().addAll(tagToBoard);

        // Picture 리스트를 생성하여 DB에 저장 및 반환
        List<Picture> pictureList = pictures.stream().map(e ->
                pictureService.createPicture(Picture.builder()
                        .board(createdBoard)
                        .path(e)
                        .build())
        ).collect(Collectors.toList());
        createdBoard.getPictures().addAll(pictureList);

        return createdBoard;
    }

    private Board findVerifiedBoard(Long boardId) {
        Optional<Board> findBoard = boardRepository.findById(boardId);
        return findBoard.orElseThrow(() -> new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND));
    }
}
