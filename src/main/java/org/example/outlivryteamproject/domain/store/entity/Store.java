package org.example.outlivryteamproject.domain.store.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import org.example.outlivryteamproject.common.BaseEntity;
import org.example.outlivryteamproject.domain.store.dto.request.StoreRequsetDto;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Entity
@Table(name = "stores")
@SQLDelete(sql = "UPDATE users SET store_deleted = true WHERE id = ?") // delete 기능을 store_deleted = true 바꿈
@Where(clause = "store_deleted = false") // store_deleted = false 만 entity로 전달함 -> store_deleted = true는 숨김
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long storeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    @Column(name = "store_name", nullable = false, unique = true)
    private String storeName;

    @Column(name = "store_picture_url")
    private String stroePictureUrl;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "content")
    private String content;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "min_delivery_price", nullable = false)
    private Long minDeliveryPrice;

    @Column(name = "delivery_tip", nullable = false)
    private Long deliveryTip;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Column(name = "operation_hours")
    private String operationHours;

    @Column(name = "closed_days")
    private String closedDays;

    public Store(StoreRequsetDto requsetDto) {
        this.storeName = requsetDto.getStoreName();
        this.stroePictureUrl = requsetDto.getStroePictureUrl();
        this.phone = requsetDto.getPhone();
        this.address = requsetDto.getAddress();
        this.content = requsetDto.getContent();
        this.category = requsetDto.getCategory();
        this.minDeliveryPrice = requsetDto.getMinDeliveryPrice();
        this.deliveryTip = requsetDto.getDeliveryTip();
        this.deliveryAddress = requsetDto.getDeliveryAddress();
        this.operationHours = requsetDto.getOperationHours();
        this.closedDays = requsetDto.getClosedDays();
    }
}
