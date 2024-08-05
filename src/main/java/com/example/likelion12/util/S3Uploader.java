package com.example.likelion12.util;

import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import com.amazonaws.services.s3.AmazonS3;
@RequiredArgsConstructor
@Component
@Slf4j
public class S3Uploader {

    private final AmazonS3 amazonS3;
    @Value("${spring.s3.bucket-name}")
    private String bucketName;
    @Value("${spring.s3.secret-key}")
    private String secretKey;

    public String uploadFileToS3(MultipartFile file, String folder) throws IOException {
        log.info("[S3Uploader.uploadFileToS3]");
        String fileName = folder + UUID.randomUUID().toString() + "_" + file.getOriginalFilename(); /** 템플릿 코드 : 고유한 아이디를 부여하는 코드라고 합니다! */
        try (InputStream inputStream = file.getInputStream()) {
            amazonS3.putObject(new PutObjectRequest(bucketName, fileName, inputStream, null));
        }
        String fileUrl = String.valueOf(amazonS3.getUrl(bucketName,fileName));
        return fileUrl;
    }

}

