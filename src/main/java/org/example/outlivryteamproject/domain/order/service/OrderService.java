package org.example.outlivryteamproject.domain.order.service;

import org.example.outlivryteamproject.domain.order.dto.responseDto.OrderResponseDto;

public interface OrderService {

    OrderResponseDto createOrder(Long userId);
}
