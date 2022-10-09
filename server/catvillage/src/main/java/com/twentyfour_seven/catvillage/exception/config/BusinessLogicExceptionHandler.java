package com.twentyfour_seven.catvillage.exception.config;

import com.twentyfour_seven.catvillage.exception.BusinessLogicException;
import com.twentyfour_seven.catvillage.exception.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BusinessLogicExceptionHandler {
    @ExceptionHandler(BusinessLogicException.class)
    public ResponseEntity<?> handleCustomRuntimeException(BusinessLogicException ex) {
        ErrorResponse response = new ErrorResponse(ex.getExceptionCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getExceptionCode().getStatus()));
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<?> handleException(Exception ex) {
//        ErrorResponse response = new ErrorResponse(500, ex.getMessage());
//        if(ex.getMessage() == null) {
//            response.setMessage("Internal server error");
//        } else if (ex.getMessage().equals("자격 증명에 실패하였습니다.")) {
//                response.setStatus(401);
//        }
//        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
//    }
}
