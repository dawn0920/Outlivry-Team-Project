package org.example.outlivryteamproject.domain.order.controller;

import lombok.RequiredArgsConstructor;
import org.example.outlivryteamproject.common.response.ApiResponse;
import org.example.outlivryteamproject.domain.order.dto.responseDto.OrderResponseDto;
import org.example.outlivryteamproject.domain.order.service.OrderServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderServiceImpl orderService;

    @PostMapping("/users/{userId}")
    public ResponseEntity<ApiResponse<OrderResponseDto>> createOrder(@PathVariable Long userId) {

        OrderResponseDto createOrder = orderService.createOrder(userId);

        return new ResponseEntity<>(new ApiResponse<>("주문에 성공하였습니다.", createOrder), HttpStatus.CREATED);
    }
}
