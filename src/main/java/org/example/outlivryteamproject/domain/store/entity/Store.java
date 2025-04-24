package org.example.outlivryteamproject.domain.store.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.outlivryteamproject.common.BaseEntity;
import org.example.outlivryteamproject.domain.menu.entity.Menu;
import org.example.outlivryteamproject.domain.store.dto.request.StoreRequestDto;
import org.example.outlivryteamproject.domain.user.entity.User;
import org.hibernate.annotations.Where;

@Setter
@Getter
@Entity
@Table(name = "stores")
@NoArgsConstructor
@Where(clause = "is_deleted = false") // is_deleted = false 만 entity로 전달함 -> is_deleted = true는 숨김
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long storeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY)
    private List<Menu> menuList = new ArrayList<>();

    @Column(name = "store_name", nullable = false, unique = true)
    private String storeName;

    @Column(name = "store_picture_url")
    private String storePictureUrl;

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

    @Column(name = "open_time")
    private String openTime;

    @Column(name = "close_time")
    private String closeTime;


    public Store(StoreRequestDto requestDto, User user, String storePictureUrl) {
        this.user = user;
        this.storeName = requestDto.getStoreName();
        this.storePictureUrl = storePictureUrl;
        this.phone = requestDto.getPhone();
        this.address = requestDto.getAddress();
        this.content = requestDto.getContent();
        this.category = requestDto.getCategory();
        this.minDeliveryPrice = requestDto.getMinDeliveryPrice();
        this.deliveryTip = requestDto.getDeliveryTip();
        this.openTime = requestDto.getOpenTime();
        this.closeTime = requestDto.getCloseTime();
    }
}
