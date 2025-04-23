package org.example.outlivryteamproject.domain.store.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StoreRequsetDto {

    @NotBlank
    private final String storeName; // 가게 이름

    private final String stroePictureUrl; // 가게 사진

    @NotBlank
    private final String phone; // 가게 번호

    @NotBlank
    private final String address; // 가게 주소

    private final String content; // 가게 소개글

    @NotBlank
    private final String category;  // 가게 음식 카테고리

    @NotBlank
    private final Long minDeliveryPrice;  // 가게 최소 주문금액

    @NotBlank
    private final Long deliveryTip; // 가게 배달료

    private final String deliveryAddress; // 가게 배달지역

    private final String operationHours;  // 가게 운영시간

    private final String closedDays; // 가게 휴무일
}
