package com.twentyfour_seven.catvillage.common.picture.controller;

import com.twentyfour_seven.catvillage.common.picture.dto.PictureDto;
import com.twentyfour_seven.catvillage.common.picture.service.S3UploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

//@Tag(name = "Upload", description = "파일 업로드 API")
@RequiredArgsConstructor
@RestController
public class FileUploadController {
    private final S3UploadService s3UploadService;

    @Operation(summary = "이미지 업로드", description = "이미지를 업로드 합니다",
    responses = {
            @ApiResponse(responseCode = "201", description = "이미지 업로드 성공", content = @Content(schema = @Schema(implementation = String.class, description = "이미지 저장 경로(URL)")))
    })
    @PostMapping("/upload")
    public ResponseEntity uploadFile(@RequestParam("images") MultipartFile multipartFile) throws IOException {
        return new ResponseEntity<>(s3UploadService.upload(multipartFile), HttpStatus.CREATED);
    }

    @Operation(summary = "사용하지 않는 이미지 삭제", description = "S3에 업로드한 이미지 삭제",
        responses = {
            @ApiResponse(responseCode = "204", description = "이미지 삭제 성공")
        }
    )
    @DeleteMapping("/images/delete")
    public ResponseEntity deleteFile(@RequestBody PictureDto pictureDto) {
        s3UploadService.delete(pictureDto.getPicture());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
