package com.twentyfour_seven.catvillage.cat.controller;

import com.twentyfour_seven.catvillage.cat.dto.CatPostDto;
import com.twentyfour_seven.catvillage.cat.dto.CatResponseDto;
import com.twentyfour_seven.catvillage.cat.dto.CatTagResponseDto;
import com.twentyfour_seven.catvillage.cat.entity.Cat;
import com.twentyfour_seven.catvillage.cat.entity.CatTag;
import com.twentyfour_seven.catvillage.cat.mapper.CatMapper;
import com.twentyfour_seven.catvillage.cat.mapper.CatTagMapper;
import com.twentyfour_seven.catvillage.cat.service.CatService;
import com.twentyfour_seven.catvillage.cat.service.CatTagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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

//@Tag(name = "Cat", description = "고양이 API")
@RestController
@RequestMapping("/cats")
@Transactional
@Validated
public class CatController {
    private final CatService catService;
    private final CatMapper catMapper;
    private final CatTagMapper catTagMapper;
    private final CatTagService catTagService;

    public CatController(CatService catService, CatMapper catMapper,
                         CatTagMapper catTagMapper, CatTagService catTagService) {
        this.catService = catService;
        this.catMapper = catMapper;
        this.catTagMapper = catTagMapper;
        this.catTagService = catTagService;
    }

    @Operation(summary = "고양이 등록하기",
            description = "로그인한 유저 정보를 꺼내와서 유저에 고양이를 추가합니다. 만약 등록되지 않은 유저일 경우 404 에러가 납니다.",
    responses = {
            @ApiResponse(responseCode = "201", description = "고양이 등록 성공", content = @Content(schema = @Schema(implementation = CatResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 유저")
    })
    @PostMapping
    public ResponseEntity postCat(@Valid @RequestBody CatPostDto catPostDto,
                                  @AuthenticationPrincipal User user) {
        Cat cat = catMapper.catPostDtoToCat(catPostDto);
        String breed = catPostDto.getBreed();
        List<CatTag> catTags = catTagMapper.catTagPostDtosToCatTags(catPostDto.getTags());

        Cat createCat = catService.createCat(cat, breed, user.getUsername());
        List<CatTag> createCatTags = catTagService.saveTag(catTags, createCat);

        CatResponseDto response = catMapper.catToCatResponseDto(createCat);
        response.setTags(catTagMapper.catTagsToCatTagResponseDtos(createCatTags));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "고양이 프로필 정보 불러오기",
            description = "로그인 하지 않은 유저도 요청 가능합니다.",
    responses = {
            @ApiResponse(responseCode = "200", description = "고양이 프로필 조회 성공", content = @Content(schema = @Schema(implementation = CatResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 고양이 프로필")
    })
    @GetMapping("/{cats-id}")
    public ResponseEntity getCat(@PathVariable("cats-id") @Positive long catId) {
        Cat cat = catService.findCat(catId);
        CatResponseDto catResponseDto = catMapper.catToCatResponseDto(cat);
        List<CatTag> catTags = catTagMapper.tagToCatsToCatTags(cat.getTagToCats());
        List<CatTagResponseDto> catTagResponseDtos = catTagMapper.catTagsToCatTagResponseDtos(catTags);
        catResponseDto.setTags(catTagResponseDtos);
        return new ResponseEntity<>(catResponseDto, HttpStatus.OK);
    }

    @Operation(summary = "고양이 프로필 수정하기",
            description = "로그인된 유저 정보와 처음 고양이를 등록했던 유저 정보가 일치하지 않을 경우 405 에러가 납니다.",
    responses = {
            @ApiResponse(responseCode = "200", description = "고양이 프로필 수정 성공", content = @Content(schema = @Schema(implementation = CatResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 고양이 프로필"),
            @ApiResponse(responseCode = "405", description = "유저 정보 불일치")
    })
    @PatchMapping("/{cats-id}")
    public ResponseEntity patchCat(@PathVariable("cats-id") @Positive long catId,
                                   @Valid @RequestBody CatPostDto catPostDto,
                                   @AuthenticationPrincipal User user) {
        Cat cat = catMapper.catPostDtoToCat(catPostDto);
        String breed = catPostDto.getBreed();
        List<CatTag> catTags = catTagMapper.catTagPostDtosToCatTags(catPostDto.getTags());

        Cat updateCat = catService.updateCat(catId, cat, breed, user.getUsername());
        List<CatTag> updateTag = catTagService.updateTag(catTags, updateCat);

        CatResponseDto response = catMapper.catToCatResponseDto(updateCat);
        response.setTags(catTagMapper.catTagsToCatTagResponseDtos(updateTag));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "고양이 프로필 삭제하기",
            description = "로그인된 유저 정보와 처음 고양이를 등록했던 유저 정보가 일치하지 않을 경우 405 에러가 납니다.",
    responses = {
            @ApiResponse(responseCode = "204", description = "고양이 프로필 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 고양이 프로필")
    })
    @DeleteMapping("/{cats-id}")
    public ResponseEntity deleteCat(@PathVariable("cats-id") @Positive long catId,
                                    @AuthenticationPrincipal User user) {
        catService.removeCat(catId, user.getUsername());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
