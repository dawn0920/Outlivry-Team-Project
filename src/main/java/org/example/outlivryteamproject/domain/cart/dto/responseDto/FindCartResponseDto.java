package org.example.outlivryteamproject.domain.cart.dto.responseDto;

import lombok.Getter;
import org.example.outlivryteamproject.domain.cart.entity.Cart;

@Getter
public class FindCartResponseDto {

    private final Long cartId;

    private final Long menuId;

    private final String menuName;

    private final Integer price;

    private final Integer quantity;

    public FindCartResponseDto(Cart cart) {
        this.cartId = cart.getCartId();
        this.menuId = cart.getMenu().getMenuId();
        this.menuName = cart.getMenuName();
        this.price = cart.getPrice();
        this.quantity = cart.getQuantity();
    }
}
