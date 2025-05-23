package org.example.outlivryteamproject.domain.store.dto.response;

import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.outlivryteamproject.domain.store.entity.Store;

@Getter
@RequiredArgsConstructor
public class StoreResponseDto {

    private Long storeId;
    private Long userId;
    private String storeName;
    private String storePictureUrl;
    private String phone;
    private String address;
    private String content;
    private String category;
    private Long minDeliveryPrice;
    private Long deliveryTip;
    private LocalTime openTime;
    private LocalTime closeTime;
    private LocalDateTime createTime;
    private LocalDateTime modifiedTime;
    private boolean isDeleted;

    public StoreResponseDto(Store store) {
        this.storeId = store.getStoreId();
        this.userId = store.getUser().getId();
        this.storeName = store.getStoreName();
        this.storePictureUrl = store.getStorePictureUrl();
        this.phone = store.getPhone();
        this.address = store.getAddress();
        this.content = store.getContent();
        this.category = store.getCategory();
        this.minDeliveryPrice = store.getMinDeliveryPrice();
        this.deliveryTip = store.getDeliveryTip();
        this.openTime = store.getOpenTime();
        this.closeTime = store.getCloseTime();
        this.createTime = store.getCreateTime();
        this.modifiedTime = store.getModifiedTime();
        this.isDeleted = store.isDeleted();
    }
}
