package com.twentyfour_seven.catvillage.common.picture.controller;

import com.twentyfour_seven.catvillage.common.picture.service.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class FileUploadController {
    private final S3UploadService s3UploadService;

    @PostMapping("/upload")
    public ResponseEntity uploadFile(@RequestParam("images") MultipartFile multipartFile) throws IOException {
        return new ResponseEntity<>(s3UploadService.upload(multipartFile), HttpStatus.CREATED);
    }
}
