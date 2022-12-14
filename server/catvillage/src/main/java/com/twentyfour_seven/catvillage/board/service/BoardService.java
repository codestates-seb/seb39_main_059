package com.twentyfour_seven.catvillage.board.service;

import com.twentyfour_seven.catvillage.board.dto.BoardTagDto;
import com.twentyfour_seven.catvillage.board.entity.Board;
import com.twentyfour_seven.catvillage.board.entity.TagToBoard;
import com.twentyfour_seven.catvillage.board.repository.BoardRepository;
import com.twentyfour_seven.catvillage.common.like.LikeService;
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
    private LikeService likeService;

    public BoardService(BoardRepository boardRepository, TagToBoardService tagToBoardService,
                        PictureService pictureService, LikeService likeService) {
        this.boardRepository = boardRepository;
        this.tagToBoardService = tagToBoardService;
        this.pictureService = pictureService;
        this.likeService = likeService;
    }

    public Page<Board> findBoards(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("boardId").descending());
        return boardRepository.findAll(pageRequest);
    }

    public Board findBoard(Long boardId) {
        return findVerifiedBoard(boardId);
    }

    public Board createBoard(Board board, List<BoardTagDto> tags, List<PictureDto> pictures) {
        // ID??? ?????? ?????? ?????? ??????
        Board createdBoard = boardRepository.save(board);

        // TagToBoard ???????????? ???????????? DB??? ?????? ??? ??????
        List<TagToBoard> tagToBoard = tagToBoardService.createTagToBoard(createdBoard, tags);
        // createdBoard ????????? TagToBoard ????????? ??????
        createdBoard.getTagToBoards().addAll(tagToBoard);

        // Picture ???????????? ???????????? DB??? ?????? ??? ??????
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

        // ?????? ?????? : ????????? ????????? ??? ??????
        if (!board.getTitle().isEmpty()) {
            findBoard.setTitle(board.getTitle());
        }

        // ?????? ?????? : ????????? ????????? ??? ??????.
        if (board.getBody() != null) {
            findBoard.setBody(board.getBody());
        }

        // ????????? ?????? ??????
        findBoard.getTagToBoards().forEach(e -> {
            if (!tags.contains(new BoardTagDto(e.getBoardTag()))) {
                tagToBoardService.deleteTagToBoard(e);
            }
        });
        findBoard.getTagToBoards().removeIf(e -> !tags.contains(new BoardTagDto(e.getBoardTag())));

        // ????????? ?????? ???????????? ??????
        List<TagToBoard> tagToBoards = tagToBoardService.updateTagToBoard(findBoard, tags);
        findBoard.getTagToBoards().addAll(tagToBoards);

        // ?????? ?????? ??????????????? ????????? ???????????? ?????? ????????? ??????
        // ????????? ?????? ????????? DB ??? ??????????????? ??????
        findBoard.getPictures().forEach(e -> {
            PictureDto dto = new PictureDto(e);
            if (pictures.contains(dto)) {
                pictures.remove(dto);
            } else {
                pictureService.removePicture(e.getPictureId());
            }
        });
        findBoard.getPictures().removeIf(e -> !pictures.contains(new PictureDto(e)));

        // ????????? ?????? ?????? ??????
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

    public void addLike(long boardId, User user) {
        // board id ??? ???????????? ??????????????? ???????????? ????????? ?????? ?????????
        Board findBoard = findVerifiedBoard(boardId);

        // like ??????
        likeService.addLikeInBoard(user, findBoard);

        // board ??? likeCount 1 ????????? ??????
        findBoard.setLikeCount(findBoard.getLikeCount() + 1);
        boardRepository.save(findBoard);
    }

    public void deleteLike(long boardId, User user) {
        // board id ??? ???????????? ???????????? ???????????? ?????? ?????? ?????????
        Board findBoard = findVerifiedBoard(boardId);

        // like ??????
        likeService.deleteLikeInBoard(user, findBoard);

        // board ??? likeCount 1 ?????? ??????
        findBoard.setLikeCount(findBoard.getLikeCount() - 1);
        boardRepository.save(findBoard);
    }
}
