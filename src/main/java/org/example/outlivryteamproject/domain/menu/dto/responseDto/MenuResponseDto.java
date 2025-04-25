package org.example.outlivryteamproject.domain.menu.dto.responseDto;

import lombok.Getter;
import org.example.outlivryteamproject.domain.menu.entity.Menu;

import java.time.LocalDateTime;

@Getter
public class MenuResponseDto {
    private Long menuId;

    private Long storeId;

    private String menuName;

    private Integer price;

    private String imageUrl;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    private boolean soldOut;

    private boolean isDeleted;

    public MenuResponseDto(Menu menu) {
        this.menuId = menu.getMenuId();
        this.storeId = menu.getStore().getStoreId();
        this.menuName = menu.getMenuName();
        this.price = menu.getPrice();
        this.imageUrl = menu.getImageUrl();
        this.createdDate = menu.getCreateTime();
        this.modifiedDate = menu.getModifiedTime();
        this.soldOut = menu.isSoldOut();
        this.isDeleted = menu.isDeleted();
    }
}
