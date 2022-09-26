package com.twentyfour_seven.catvillage.board.service;

import com.twentyfour_seven.catvillage.board.repository.TagToBoardRepository;
import org.springframework.stereotype.Service;

@Service
public class TagToBoardService {
    private TagToBoardRepository tagToBoardRepository;

    public TagToBoardService(TagToBoardRepository tagToBoardRepository) {
        this.tagToBoardRepository = tagToBoardRepository;
    }
}
