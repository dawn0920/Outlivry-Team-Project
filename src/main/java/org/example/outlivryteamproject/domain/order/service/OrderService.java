package org.example.outlivryteamproject.domain.order.service;

import org.example.outlivryteamproject.domain.order.dto.requestDto.OrderRequestDto;
import org.example.outlivryteamproject.domain.order.dto.responseDto.OrderResponseDto;

public interface OrderService {

    OrderResponseDto createOrder(Long storeId, Long userId, OrderRequestDto requestDto);

    OrderResponseDto findByOrderId(Long userId, Long orderId);

    OrderResponseDto receivedOrder(Long userId, Long orderId);

    OrderResponseDto deliveryOrder(Long userId, Long orderId);

    void deleteOrder(Long userId, Long orderId);
}
