package com.twentyfour_seven.catvillage.common.picture.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class S3UploadService {

    private static final int CAPACITY_LIMIT_BYTE = 4194304;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.dir}")
    private String filePath;

    private final AmazonS3 amazonS3;

    public String upload(MultipartFile multipartFile) throws IOException {
        // 파일 이름이 중복되지 않게 하기 위해 UUID 로 랜덤값 생성하여 "-"로 파일 이름과 연결하여 파일 이름 생성
        String s3FileName = filePath + UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

        // 파일의 크기가 용량제한을 넘을 시 예외를 던진다.
        if (multipartFile.getInputStream().available() > CAPACITY_LIMIT_BYTE) {
            throw new RuntimeException("이미지가 4M 제한을 넘어갑니다.");
        }
        // S3에 알려줄 파일 메타 데이터 정보에 파일 크기를 담는다.
        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentLength(multipartFile.getInputStream().available());

        amazonS3.putObject(bucket, s3FileName, multipartFile.getInputStream(), objMeta);

        return amazonS3.getUrl(bucket, s3FileName).toString();
    }
}
