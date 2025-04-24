package org.example.outlivryteamproject.domain.store.dto.request;


import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class updateStoreRequestDto {

    private final String newStoreName; // 가게 이름

    private final String newStorePictureUrl; // 가게 사진

    private final String newPhone; // 가게 번호

    private final String newAddress; // 가게 주소

    private final String newContent; // 가게 소개글

    private final String newCategory;  // 가게 음식 카테고리

    @Min(0)
    private final Long newMinDeliveryPrice;  // 가게 최소 주문금액

    @Min(0)
    private final Long newDeliveryTip; // 가게 배달료

    private final String newOpenTime;

    private final String newCloseTime;

}
