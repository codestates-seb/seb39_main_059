package com.twentyfour_seven.catvillage.cat.controller;

import com.twentyfour_seven.catvillage.cat.service.CatService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cats")
@Transactional
@Validated
public class CatController {
    private final CatService catService;

    public CatController(CatService catService){
        this.catService = catService;
    }
}
