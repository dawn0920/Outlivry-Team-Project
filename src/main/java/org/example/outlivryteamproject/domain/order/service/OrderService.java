package org.example.outlivryteamproject.domain.order.service;

import org.example.outlivryteamproject.domain.order.dto.requestDto.OrderRequestDto;
import org.example.outlivryteamproject.domain.order.dto.responseDto.OrderResponseDto;

public interface OrderService {

    OrderResponseDto createOrder(Long userId, OrderRequestDto requestDto);

    OrderResponseDto findByOrderId(Long orderId);

    void receivedOrder(Long orderId);

    void deliveryOrder(Long orderId);

    void deleteOrder(Long orderId);
}
