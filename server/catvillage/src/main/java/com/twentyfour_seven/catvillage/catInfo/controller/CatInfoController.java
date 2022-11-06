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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

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
                    @ApiResponse(responseCode = "409", description = "이미 등록되어 있는 품종(korName이 이미 등록되어 있는 경우 에러 발생)")
            }
    )
    @PostMapping
    public ResponseEntity postCatInfo(@RequestBody @Valid CatInfoPostDto catInfoPostDto) {
        CatInfo catInfo = catInfoMapper.catInfoPostDtoToCatInfo(catInfoPostDto);
        CatInfo createCatInfo = catInfoService.createCatInfo(catInfo);
        return new ResponseEntity<>(catInfoMapper.catInfoToCatInfoResponseDto(createCatInfo), HttpStatus.CREATED);
    }

    @Operation(summary = "모든 고양이 품종 정보 불러오기", description = "고양이 등록 시 품종 선택에 사용하는 api 입니다. 모든 데이터를 가져오지만 페이지네이션은 적절치 않아 사용하지 않습니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "모든 품종 정보 조회 성공")
            }
    )
    @GetMapping()
    public ResponseEntity getCatInfos() {
        List<CatInfo> catInfos = catInfoService.findAllCatInfo();

        return new ResponseEntity<>(catInfoMapper.catInfosToCatInfoSimpleDtos(catInfos), HttpStatus.OK);
    }

    @Operation(summary = "특정 고양이 품종 정보 조회", description = "특정 고양이 품종 페이지에 사용되는 api 입니다. 엔드포인트로 품종의 한국어 이름을 보내주셔야 합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "특정 품종 정보 조회 성공"),
                    @ApiResponse(responseCode = "404", description = "찾을 수 없는 품종 정보")
            }
    )
    @GetMapping("{breeds-id}")
    public ResponseEntity getCatInfo(@PathVariable("breeds-id") @Positive long catInfoId) {
        CatInfo findCatInfo = catInfoService.findById(catInfoId);
        return new ResponseEntity<>(catInfoMapper.catInfoToCatInfoResponseDto(findCatInfo), HttpStatus.OK);
    }
}
