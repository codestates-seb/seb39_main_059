package com.twentyfour_seven.catvillage.dto;

import com.twentyfour_seven.catvillage.board.dto.BoardMultiGetResponse;
import org.springframework.data.domain.Page;

import java.util.List;

// 해당 클래스는 Swagger에서 반환 class로 사용하기 위해 만든 클래스
public class MultiBoardResponseDto extends MultiResponseDto<BoardMultiGetResponse>{
    public MultiBoardResponseDto(List<BoardMultiGetResponse> items, Page pageInfo) {
        super(items, pageInfo);
    }
}
