package org.example.outlivryteamproject.domain.order.dto;

import lombok.Getter;
import org.example.outlivryteamproject.domain.cart.entity.Cart;
import org.example.outlivryteamproject.domain.order.entity.OrderItem;

@Getter
public class OrderItemConverter {

    private final String menuName;

    private final Integer price;

    private final Integer quantity;

    public OrderItemConverter(OrderItem orderItem) {
        this.menuName = orderItem.getMenuName();
        this.price = orderItem.getPrice();
        this.quantity = orderItem.getQuantity();
    }
}
