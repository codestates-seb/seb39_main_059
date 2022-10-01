package com.twentyfour_seven.catvillage.common.health_check;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HealthCheckController {
    @GetMapping
    public ResponseEntity getHealthCheck() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}