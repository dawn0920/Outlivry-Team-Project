package org.example.outlivryteamproject.domain.store.dto.request;


import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class updateStoreRequestDto {

    private String newStoreName; // 가게 이름

    private MultipartFile storePicture; // 가게 사진

    private String newPhone; // 가게 번호

    private String newAddress; // 가게 주소

    private String newContent; // 가게 소개글

    private String newCategory;  // 가게 음식 카테고리

    @Min(0)
    private Long newMinDeliveryPrice;  // 가게 최소 주문금액

    @Min(0)
    private Long newDeliveryTip; // 가게 배달료

    private String newOpenTime;

    private String newCloseTime;

}
