package org.example.outlivryteamproject.domain.menu.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class MenuRequestDto {

    @Setter
    private Long userId;

    @Setter
    private Long storeId;

    private String menuName;

    private Integer price;

    private String image;

    public MenuRequestDto(String menuName, Integer price, String image) {
        this.menuName = menuName;
        this.price = price;
        this.image = image;
    }
}
