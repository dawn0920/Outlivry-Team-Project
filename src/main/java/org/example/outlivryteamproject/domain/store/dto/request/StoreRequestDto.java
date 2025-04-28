package org.example.outlivryteamproject.domain.store.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class StoreRequestDto {

    @NotBlank
    private String storeName; // 가게 이름

    private MultipartFile storePicture; // 가게 사진

    @NotBlank
    private String phone; // 가게 번호

    @NotBlank
    private String address; // 가게 주소

    private String content; // 가게 소개글

    @NotBlank
    private String category;  // 가게 음식 카테고리

    @NotNull
    @Min(0)
    private Long minDeliveryPrice;  // 가게 최소 주문금액

    @NotNull
    @Min(0)
    private Long deliveryTip; // 가게 배달료

    @NotNull
    @JsonFormat(pattern = "HH:mm")
    private LocalTime openTime;

    @NotNull
    @JsonFormat(pattern = "HH:mm")
    private LocalTime closeTime;
}
