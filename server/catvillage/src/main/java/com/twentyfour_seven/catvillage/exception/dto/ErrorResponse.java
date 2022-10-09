package com.twentyfour_seven.catvillage.exception.dto;

import com.twentyfour_seven.catvillage.exception.ExceptionCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private int status;
    private String message;

    public ErrorResponse(ExceptionCode exceptionCode) {
        this.status = exceptionCode.getStatus();
        this.message = exceptionCode.getMessage();
    }

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
