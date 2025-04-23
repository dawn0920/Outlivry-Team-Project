package org.example.outlivryteamproject.domain.store.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StoreRequsetDto {

    @NotBlank
    private String storeName; // 가게 이름

    private String stroePictureUrl; // 가게 사진

    @NotBlank
    private String phone; // 가게 번호

    @NotBlank
    private String address; // 가게 주소

    private String content; // 가게 소개글

    @NotBlank
    private String category;  // 가게 음식 카테고리

    @NotBlank
    private Long minDeliveryPrice;  // 가게 최소 주문금액

    @NotBlank
    private Long deliveryTip; // 가게 배달료

    private String deliveryAddress; // 가게 배달지역

    private String operationHours;  // 가게 운영시간

    private String closedDays; // 가게 휴무일
}
