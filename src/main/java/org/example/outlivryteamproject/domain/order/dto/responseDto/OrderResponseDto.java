package org.example.outlivryteamproject.domain.order.dto.responseDto;

import lombok.Getter;
import org.example.outlivryteamproject.domain.order.dto.OrderItemConverter;
import org.example.outlivryteamproject.domain.order.entity.Order;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class OrderResponseDto {

    private final Long storeId;

    private final Long orderId;

    private final Integer totalPrice;

    private final List<OrderItemConverter> orderItems;

    private final boolean isReceived;

    private final boolean isDelivery;

    private final LocalDateTime creatTime;

    public OrderResponseDto(Order order) {
        this.storeId = order.getStore().getStoreId();
        this.orderId = order.getOrderId();
        this.totalPrice = order.getTotalPrice();
        this.orderItems = order.getOrderItems().stream()
                .map(OrderItemConverter::new)
                .toList();
        this.isReceived = order.isReceived();
        this.isDelivery = order.isDelivery();
        this.creatTime = order.getCreateTime();
    }
}
