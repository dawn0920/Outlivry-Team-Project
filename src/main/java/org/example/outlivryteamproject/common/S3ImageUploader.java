package org.example.outlivryteamproject.common;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3ImageUploader {

    private final AmazonS3 amazonS3Client;

    public String uploadImage(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            return null;
        }

        if (!isValidImage(file)) {
            throw new RuntimeException("이미지 파일만 업로드할 수 있습니다.");
        }

        try {
            // S3에 업로드할 파일 이름 생성 (예: UUID와 시간정보로 중복 방지)
            String fileName = UUID.randomUUID() + "-" + System.currentTimeMillis() + "-" + file.getOriginalFilename();

            // S3 업로드 설정
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());

            // S3에 파일 업로드
            // file.getInputStream() 바이트 코드
            String bucketName = "janghwal-image-bucket-1";
            amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), metadata));

            // S3에서 파일의 URL을 반환 (버킷 + 파일 이름)
            return amazonS3Client.getUrl(bucketName, fileName).toString();

        } catch (IOException e) {
            throw new RuntimeException("이미지 업로드 중 오류가 발생했습니다.", e);
        }
    }

    private boolean isValidImage(MultipartFile file) {

        // 이미지 Content-Type 확인
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }
}
