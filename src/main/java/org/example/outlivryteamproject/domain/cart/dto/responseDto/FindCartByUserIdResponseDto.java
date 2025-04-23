package org.example.outlivryteamproject.domain.cart.dto.responseDto;

import lombok.Getter;

@Getter
public class FindCartByUserIdResponseDto {

    private final Long cartId;

    private final String menuName;

    private final Integer totalPrice;

    public FindCartByUserIdResponseDto(Long cartId, String menuName, Integer totalPrice) {
        this.cartId = cartId;
        this.menuName = menuName;
        this.totalPrice = totalPrice;
    }
}
