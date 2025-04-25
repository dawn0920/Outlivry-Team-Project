package org.example.outlivryteamproject.domain.cart.dto.responseDto;

import lombok.Getter;
import org.example.outlivryteamproject.domain.cart.entity.Cart;

@Getter
public class FindCartResponseDto {

    private final Long cartId;

    private final String menuName;

    private final Integer price;

    public FindCartResponseDto(Cart cart) {
        this.cartId = cart.getCartId();
        this.menuName = cart.getMenuName();
        this.price = cart.getPrice();
    }
}
