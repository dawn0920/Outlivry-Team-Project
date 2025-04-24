package org.example.outlivryteamproject.domain.order.dto.responseDto;

import lombok.Getter;
import org.example.outlivryteamproject.domain.order.dto.CartItemDto;
import org.example.outlivryteamproject.domain.order.entity.Order;

import java.util.List;

@Getter
public class OrderResponseDto {

    private final Long orderId;

    private final Integer totalPrice;

    private final List<CartItemDto> cartItems;

    private final boolean received;

    private final boolean delivery;

    public OrderResponseDto(Order order) {
        this.orderId = order.getOrderId();
        this.totalPrice = order.getTotalPrice();
        this.cartItems = order.getCarts().stream()
                .map(CartItemDto::new)
                .toList();
        this.received = order.isReceived();
        this.delivery = order.isDelivery();
    }
}
