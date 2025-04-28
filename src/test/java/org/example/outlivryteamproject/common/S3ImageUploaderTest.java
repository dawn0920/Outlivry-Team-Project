package org.example.outlivryteamproject.common;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.example.outlivryteamproject.domain.menu.dto.requestDto.ModifiedMenuRequestDto;
import org.example.outlivryteamproject.exception.CustomException;
import org.example.outlivryteamproject.exception.ExceptionCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class S3ImageUploaderTest {

    @Mock
    private AmazonS3 amazonS3Client;

    @Mock
    private MockMultipartFile mockMultipartFile;

    @InjectMocks
    private S3ImageUploader s3ImageUploader;

    @Test
    void uploadImage() throws Exception {
        // given
        MockMultipartFile mockFile = new MockMultipartFile(
                "image",
                "sample.jpg",
                "image/jpeg",
                "fake image content".getBytes()
        );

        // S3에 파일 저장하면 URL을 반환한다고 가정
        when(amazonS3Client.getUrl(any(), any())).thenReturn(new URL("https://fake-s3-url.com/sample.jpg"));

        // when
        String uploadedUrl = s3ImageUploader.uploadImage(mockFile);

        // then
        assertThat(uploadedUrl).isEqualTo("https://fake-s3-url.com/sample.jpg");

        // 실제 S3 putObject가 호출됐는지 검증
        verify(amazonS3Client, times(1)).putObject(any(PutObjectRequest.class));
    }

    @Test
    void uploadImage_null() {
        // given
        MockMultipartFile mockFile = new MockMultipartFile(
                "image",
                "sample.jpg",
                "image/jpeg",
                new byte[0]  // 빈 파일
        );

        // when
        String uploadedUrl = s3ImageUploader.uploadImage(mockFile);

        // then
        assertThat(uploadedUrl).isNull();  // 빈 파일은 null을 반환해야 함
    }

    @Test
    void uploadImage_ex_no_image() {
        // given
        MockMultipartFile mockFile = new MockMultipartFile(
                "document",
                "sample.txt",
                "text/plain",  // 이미지가 아닌 타입
                "fake document content".getBytes()
        );

        // when & then
        assertThatThrownBy(() -> s3ImageUploader.uploadImage(mockFile))
                .isInstanceOf(CustomException.class)
                .hasMessage(ExceptionCode.IMAGE_FILE_REQUIRED.getMessage());
    }
}
