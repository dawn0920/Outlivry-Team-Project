package org.example.outlivryteamproject.domain.store.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.outlivryteamproject.domain.store.entity.Store;
import org.example.outlivryteamproject.domain.user.entity.User;

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
    private String openTime;
    private String closeTime;
    private LocalDateTime creatTime;
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
        this.creatTime = store.getCreatTime();
        this.modifiedTime = store.getModifiedTime();
        this.isDeleted = store.isDeleted();
    }
}
