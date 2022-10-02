package com.twentyfour_seven.catvillage.common.health_check;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Tag(name = "HealthCheck", description = "서버 헬스체크 API")
@RestController
@RequestMapping("/")
public class HealthCheckController {
    @Operation(summary = "헬스 체크용 API", description = "헬스 체크를 위한 용도")
    @ApiResponse(responseCode = "200", description = "통신 성공")
    @GetMapping
    public ResponseEntity getHealthCheck() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}