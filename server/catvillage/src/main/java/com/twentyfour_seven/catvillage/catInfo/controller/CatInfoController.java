package com.twentyfour_seven.catvillage.catInfo.controller;

import com.twentyfour_seven.catvillage.catInfo.service.CatInfoService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/집사생활/cats")
@Transactional
@Validated
public class CatInfoController {
    private final CatInfoService catInfoService;

    public CatInfoController(CatInfoService catInfoService) {
        this.catInfoService = catInfoService;
    }
}
