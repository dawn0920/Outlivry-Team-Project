package org.example.outlivryteamproject.domain.store.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.outlivryteamproject.domain.store.entity.Store;

@Getter
@RequiredArgsConstructor
public class StoreResponseDto {

    private Long storeId;
    private User userId;
    private String storeName;
    private String stroePictureUrl;
    private String phone;
    private String address;
    private String content;
    private String category;
    private Long minDeliveryPrice;
    private Long deliveryTip;
    private String operationHours;
    private LocalDateTime creatTime;
    private LocalDateTime modifiedTime;
    private boolean storeDeleted;

    public StoreResponseDto(Store store) {
        this.storeId = store.getStoreId();
        this.userId = store.getUserId();
        this.storeName = store.getStoreName();
        this.stroePictureUrl = store.getStroePictureUrl();
        this.phone = store.getPhone();
        this.address = store.getAddress();
        this.content = store.getContent();
        this.category = store.getCategory();
        this.minDeliveryPrice = store.getMinDeliveryPrice();
        this.deliveryTip = store.getDeliveryTip();
        this.operationHours = store.getOperationHours();
        this.creatTime = store.getCreatTime();
        this.modifiedTime = store.getModifiedTime();
        this.storeDeleted = store.storeDeleted();
    }
}
