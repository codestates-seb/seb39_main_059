package com.twentyfour_seven.catvillage.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class MultiResponseDto<T> {
    private List<T> items;
    private int page;
    private int pageSize;
    private int totalPages;
    private long totalElements;

    public MultiResponseDto(List<T> items, Page pageInfo) {
        this.items = items;
        this.page = pageInfo.getNumber() + 1;
        this.pageSize = pageInfo.getSize();
        this.totalPages = pageInfo.getTotalPages();
        this.totalElements = pageInfo.getTotalElements();
    }
}
