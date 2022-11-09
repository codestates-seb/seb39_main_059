package com.twentyfour_seven.catvillage.board.service;

import com.twentyfour_seven.catvillage.board.dto.BoardTagDto;
import com.twentyfour_seven.catvillage.board.entity.BoardTag;
import com.twentyfour_seven.catvillage.board.repository.BoardTagRepository;
import com.twentyfour_seven.catvillage.exception.BusinessLogicException;
import com.twentyfour_seven.catvillage.exception.ExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BoardTagService {
    private BoardTagRepository boardTagRepository;

    public BoardTagService(BoardTagRepository boardTagRepository) {
        this.boardTagRepository = boardTagRepository;
    }

    // 집사생활의 태그는 제공되는 태그 중 선택하여 등록하는 태그이기 때문에 아래와 같은 로직은 불필요
//    public List<BoardTag> registerBoardTags(ArrayList<String> boardTags) {
//        return boardTags.stream().map(e -> {
//            BoardTag boardTag = findVerifiedTagName(e);
//            if(boardTag.getBoardTagId() == null) {
//                return boardTagRepository.save(boardTag);
//            } else {
//                return boardTag;
//            }
//        }).collect(Collectors.toList());
//    }

    public BoardTag findBoardTag(Long boardTagId) {
        return findVerifiedBoardTag(boardTagId);
    }

    public Page<BoardTag> findBoardTags(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("boardTagId").descending());
        return boardTagRepository.findAll(pageRequest);
    }

    public BoardTag addBoardTag(BoardTag boardTag) {
        verifiedExistsTagName(boardTag.getTagName());
        return boardTagRepository.save(boardTag);
    }

    public BoardTag updateBoardTag(BoardTag newBoardTag) {
        BoardTag oldBoardTag = findVerifiedBoardTag(newBoardTag.getBoardTagId());
        oldBoardTag.setTagName(newBoardTag.getTagName());
        return boardTagRepository.save(oldBoardTag);
    }

    public void removeBoardTag(long tagId) {
        BoardTag findBoardTag = findVerifiedBoardTag(tagId);
        boardTagRepository.delete(findBoardTag);
    }

    public List<BoardTag> getBoardTags(List<BoardTagDto> boardTags) {
        return boardTags.stream().map(e -> findVerifiedTagName(e.getTag()))
                .collect(Collectors.toList());
    }

    private BoardTag findVerifiedBoardTag(Long boardId) {
        Optional<BoardTag> findBoard = boardTagRepository.findById(boardId);
        return findBoard.orElseThrow(() -> new BusinessLogicException(ExceptionCode.BOARD_TAG_NOT_FOUND));
    }

    private BoardTag findVerifiedTagName(String tagName) {
        Optional<BoardTag> findBoard = boardTagRepository.findByTagName(tagName);
        return findBoard.orElseThrow(() -> new BusinessLogicException(ExceptionCode.BOARD_TAG_NOT_FOUND));
        // 집사생활의 태그는 제공되는 태그 중 선택하여 등록하는 태그이기 때문에 아래와 같은 로직은 불필요
//        return findBoard.orElse(BoardTag.builder().tagName(tagName).build());
    }

    private void verifiedExistsTagName(String tagName) {
        Optional<BoardTag> findBoardTag = boardTagRepository.findByTagName(tagName);
        if (findBoardTag.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.BOARD_TAG_EXISTS);
        }
    }
}
