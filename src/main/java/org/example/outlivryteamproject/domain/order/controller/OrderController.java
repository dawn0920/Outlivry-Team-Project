package org.example.outlivryteamproject.domain.order.controller;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.example.outlivryteamproject.common.response.ApiResponse;
import org.example.outlivryteamproject.domain.order.dto.responseDto.OrderResponseDto;
import org.example.outlivryteamproject.domain.order.service.OrderServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderResponseDto>> findOrderById(@PathVariable Long orderId) {

        OrderResponseDto findedOrder = orderService.findByOrderId(orderId);

        return new ResponseEntity<>(new ApiResponse<>("주문 조회", findedOrder), HttpStatus.OK);
    }

    @PatchMapping("/{orderId}/received")
    public ResponseEntity<ApiResponse<Void>> receivedOrder(@PathVariable Long orderId) {

        orderService.receivedOrder(orderId);

        return new ResponseEntity<>(new ApiResponse<>("주문을 수락했습니다."), HttpStatus.OK);
    }

    @PatchMapping("/{orderId}/delivery")
    public ResponseEntity<ApiResponse<Void>> deliveryOrder(@PathVariable Long orderId) {

        orderService.deliveryOrder(orderId);
    }
}
