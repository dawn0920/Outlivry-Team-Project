package org.example.outlivryteamproject.domain.cart.dto.responseDto;

import lombok.Getter;
import org.example.outlivryteamproject.domain.cart.entity.Cart;

@Getter
public class SaveCartResponseDto {

    private final Long cartId;

    private final String menuName;

    private final Integer price;

    private final Integer quantity;

    public SaveCartResponseDto(Cart cart) {
        this.cartId = cart.getCartId();
        this.menuName = cart.getMenuName();
        this.price = cart.getPrice();
        this.quantity = cart.getQuantity();
    }
}
