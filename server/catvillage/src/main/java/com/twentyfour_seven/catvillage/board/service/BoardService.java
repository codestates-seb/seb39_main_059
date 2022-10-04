package com.twentyfour_seven.catvillage.board.service;

import com.twentyfour_seven.catvillage.board.dto.BoardTagDto;
import com.twentyfour_seven.catvillage.board.entity.Board;
import com.twentyfour_seven.catvillage.board.entity.TagToBoard;
import com.twentyfour_seven.catvillage.board.repository.BoardRepository;
import com.twentyfour_seven.catvillage.common.picture.dto.PictureDto;
import com.twentyfour_seven.catvillage.common.picture.entity.Picture;
import com.twentyfour_seven.catvillage.common.picture.service.PictureService;
import com.twentyfour_seven.catvillage.exception.BusinessLogicException;
import com.twentyfour_seven.catvillage.exception.ExceptionCode;
import com.twentyfour_seven.catvillage.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BoardService {
    private BoardRepository boardRepository;
    private TagToBoardService tagToBoardService;
    private PictureService pictureService;

    public BoardService(BoardRepository boardRepository, TagToBoardService tagToBoardService, PictureService pictureService) {
        this.boardRepository = boardRepository;
        this.tagToBoardService = tagToBoardService;
        this.pictureService = pictureService;
    }

    public Page<Board> findBoards(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("boardId").descending());
        return boardRepository.findAll(pageRequest);
    }

    public Board findBoard(Long boardId) {
        return findVerifiedBoard(boardId);
    }

    public Board createBoard(Board board, List<BoardTagDto> tags, List<PictureDto> pictures) {
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
                        .path(e.getPicture())
                        .build())
        ).collect(Collectors.toList());
        createdBoard.getPictures().addAll(pictureList);

        return createdBoard;
    }

    public Board updateBoard(Board board, List<BoardTagDto> tags, List<PictureDto> pictures) {
        Board findBoard = findVerifiedBoard(board.getBoardId());
        if (!findBoard.getUser().equals(board.getUser())) {
            throw new BusinessLogicException(ExceptionCode.INVALID_USER);
        }

        // 제목 변경 : 제목은 공백일 수 없다
        if (!board.getTitle().isEmpty()) {
            findBoard.setTitle(board.getTitle());
        }

        // 본문 변경 : 본문은 공백일 수 있다.
        if (board.getBody() != null) {
            findBoard.setBody(board.getBody());
        }

        // 제거된 태그 삭제
        findBoard.getTagToBoards().forEach(e -> {
            if (!tags.contains(new BoardTagDto(e.getBoardTag()))) {
                tagToBoardService.deleteTagToBoard(e);
            }
        });
        findBoard.getTagToBoards().removeIf(e -> !tags.contains(new BoardTagDto(e.getBoardTag())));

        // 추가된 태그 리스트만 반환
        List<TagToBoard> tagToBoards = tagToBoardService.updateTagToBoard(findBoard, tags);
        findBoard.getTagToBoards().addAll(tagToBoards);

        // 사진 경로 리스트에서 기존에 존재하던 사진 항목은 제거
        // 삭제된 사진 경로는 DB 및 리스트에서 제거
        findBoard.getPictures().forEach(e -> {
            PictureDto dto = new PictureDto(e);
            if (pictures.contains(dto)) {
                pictures.remove(dto);
            } else {
                pictureService.removePicture(e.getPictureId());
            }
        });
        findBoard.getPictures().removeIf(e -> !pictures.contains(new PictureDto(e)));

        // 추가된 사진 경로 저장
        List<Picture> pictureList = pictures.stream().map(e ->
                pictureService.createPicture(Picture.builder()
                        .board(findBoard)
                        .path(e.getPicture())
                        .build())
        ).collect(Collectors.toList());
        findBoard.getPictures().addAll(pictureList);

        return boardRepository.save(findBoard);
    }

    public void deleteBoard(User user, Long boardId) {
        Board findBoard = findVerifiedBoard(boardId);
        if (!findBoard.getUser().equals(user)) {
            throw new BusinessLogicException(ExceptionCode.INVALID_USER);
        }
        boardRepository.delete(findBoard);
    }

    private Board findVerifiedBoard(Long boardId) {
        Optional<Board> findBoard = boardRepository.findById(boardId);
        return findBoard.orElseThrow(() -> new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND));
    }
}
