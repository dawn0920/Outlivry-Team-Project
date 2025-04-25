package org.example.outlivryteamproject.domain.order.dto;

import lombok.Getter;
import org.example.outlivryteamproject.domain.cart.entity.Cart;

@Getter
public class CartItemDto {

    private final String menuName;

    private final Integer price;

    private final Integer quantity;

    public CartItemDto(Cart cart) {
        this.menuName = cart.getMenuName();
        this.price = cart.getPrice();
        this.quantity = cart.getQuantity();
    }
}
