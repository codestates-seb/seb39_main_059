package com.twentyfour_seven.catvillage.cat.controller;

import com.twentyfour_seven.catvillage.cat.dto.CatPostDto;
import com.twentyfour_seven.catvillage.cat.dto.CatResponseDto;
import com.twentyfour_seven.catvillage.cat.dto.CatTagResponseDto;
import com.twentyfour_seven.catvillage.cat.entity.Cat;
import com.twentyfour_seven.catvillage.cat.entity.CatTag;
import com.twentyfour_seven.catvillage.cat.mapper.CatMapper;
import com.twentyfour_seven.catvillage.cat.mapper.CatTagMapper;
import com.twentyfour_seven.catvillage.cat.service.CatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/cats")
@Transactional
@Validated
public class CatController {
    private final CatService catService;
    private final CatMapper catMapper;
    private final CatTagMapper catTagMapper;

    public CatController(CatService catService, CatMapper catMapper,
                         CatTagMapper catTagMapper) {
        this.catService = catService;
        this.catMapper = catMapper;
        this.catTagMapper = catTagMapper;
    }

    @PostMapping
    public ResponseEntity postCat(@Valid @RequestBody CatPostDto catPostDto,
                                  @AuthenticationPrincipal User user) {
        Cat cat = catMapper.catPostDtoToCat(catPostDto);
        String breed = catPostDto.getBreed();
        List<CatTag> catTags = catTagMapper.catTagPostDtosToCatTags(catPostDto.getTags());
        Cat createCat = catService.createCat(cat, breed, catTags, user.getUsername());
        CatResponseDto catResponseDto = catMapper.catToCatResponseDto(createCat);
        catResponseDto.setTags(catTagMapper.tagToCatsToCatTagResponseDtos(createCat.getTagToCats()));
        return new ResponseEntity<>(catResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{cats-id}")
    public ResponseEntity getCat(@PathVariable("cats-id") @Positive long catId) {
        Cat cat = catService.findCat(catId);
        CatResponseDto catResponseDto = catMapper.catToCatResponseDto(cat);
        List<CatTag> catTags = catTagMapper.tagToCatsToCatTags(cat.getTagToCats());
        List<CatTagResponseDto> catTagResponseDtos = catTagMapper.catTagsToCatTagResponseDtos(catTags);
        catResponseDto.setTags(catTagResponseDtos);
        return new ResponseEntity<>(catResponseDto, HttpStatus.OK);
    }

    @PatchMapping("/{cats-id}")
    public ResponseEntity patchCat(@PathVariable("cats-id") @Positive long catId,
                                   @Valid @RequestBody CatPostDto catPostDto,
                                   @AuthenticationPrincipal User user) {
        Cat cat = catMapper.catPostDtoToCat(catPostDto);
        String breed = catPostDto.getBreed();
        List<CatTag> catTags = catTagMapper.catTagPostDtosToCatTags(catPostDto.getTags());
        Cat saveCat = catService.updateCat(cat, breed, catTags, user.getUsername());
        return new ResponseEntity<>(catMapper.catToCatResponseDto(saveCat), HttpStatus.OK);
    }

    @DeleteMapping("/{cats-id}")
    public ResponseEntity deleteCat(@PathVariable("cats-id") @Positive long catId) {
        catService.removeCat(catId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
