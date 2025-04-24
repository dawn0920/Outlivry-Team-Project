package org.example.outlivryteamproject.domain.store.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class StoreRequestDto {

    @NotBlank
    private final String storeName; // 가게 이름

    private final String storePictureUrl; // 가게 사진

    @NotBlank
    private final String phone; // 가게 번호

    @NotBlank
    private final String address; // 가게 주소

    private final String content; // 가게 소개글

    @NotBlank
    private final String category;  // 가게 음식 카테고리

    @NotNull
    @Min(0)
    private final Long minDeliveryPrice;  // 가게 최소 주문금액

    @NotNull
    @Min(0)
    private final Long deliveryTip; // 가게 배달료

    private final String openTime;

    private final String closeTime;
}
