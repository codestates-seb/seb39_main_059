package com.twentyfour_seven.catvillage.catInfo.controller;

import com.twentyfour_seven.catvillage.catInfo.dto.CatInfoPostDto;
import com.twentyfour_seven.catvillage.catInfo.entity.CatInfo;
import com.twentyfour_seven.catvillage.catInfo.mapper.CatInfoMapper;
import com.twentyfour_seven.catvillage.catInfo.service.CatInfoService;
import com.twentyfour_seven.catvillage.feed.dto.FeedCommentGetDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/집사생활/cats")
@Transactional
@Validated
public class CatInfoController {
    private final CatInfoService catInfoService;
    private final CatInfoMapper catInfoMapper;

    public CatInfoController(CatInfoService catInfoService,
                             CatInfoMapper catInfoMapper) {
        this.catInfoService = catInfoService;
        this.catInfoMapper = catInfoMapper;
    }

    @Operation(summary = "고양이 품종 정보 추가하기", description = "hairLength(털길이), hairLoss(털빠짐)은 0~3까지만 입력가능합니다.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "고양이 품종 정보 생성 성공",
                            content = @Content(schema = @Schema(implementation = FeedCommentGetDto.class))),
                    @ApiResponse(responseCode = "405", description = "유저 정보 불일치"),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 댓글")
            }
    )
    @PostMapping
    public ResponseEntity postCatInfo (@RequestBody @Valid CatInfoPostDto catInfoPostDto) {
        CatInfo catInfo = catInfoMapper.catInfoPostDtoToCatInfo(catInfoPostDto);
        CatInfo createCatInfo = catInfoService.createCatInfo(catInfo);
        return new ResponseEntity<>(catInfoMapper.catInfoToCatInfoResponseDto(createCatInfo), HttpStatus.CREATED);
    }
}
