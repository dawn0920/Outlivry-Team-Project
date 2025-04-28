package org.example.outlivryteamproject.domain.order.controller;

import lombok.RequiredArgsConstructor;
import org.example.outlivryteamproject.common.TokenUserId;
import org.example.outlivryteamproject.common.response.ApiResponse;
import org.example.outlivryteamproject.domain.order.dto.requestDto.OrderRequestDto;
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
    private final TokenUserId tokenUserId;

    /**
     * 주문하기
     *
     * @param authHeader 로그인 유저 정보
     * @param requestDto 요청사항
     * @return ResponseEntity
     */
    @PostMapping("/stores/{storeId}")
    public ResponseEntity<ApiResponse<OrderResponseDto>> createOrder(
            @PathVariable Long storeId,
            @RequestHeader("Authorization") String authHeader,
            @RequestBody OrderRequestDto requestDto
    ) {
        Long userId = tokenUserId.getTokenUserId(authHeader);
        OrderResponseDto createOrder = orderService.createOrder(storeId, userId, requestDto);

        return new ResponseEntity<>(new ApiResponse<>("주문 완료", createOrder), HttpStatus.CREATED);
    }

    /**
     * 주문 조회
     *
     * @param authHeader 로그인 유저 정보
     * @param orderId 주문 정보
     * @return ResponseEntity
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderResponseDto>> findOrderById(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long orderId
    ) {
        Long userId = tokenUserId.getTokenUserId(authHeader);
        OrderResponseDto findedOrder = orderService.findByOrderId(userId, orderId);

        return new ResponseEntity<>(new ApiResponse<>("주문 조회", findedOrder), HttpStatus.OK);
    }

    /**
     * 주문 수락 여부
     *
     * @param authHeader 로그인 유저 정보
     * @param orderId 주문 정보
     * @return ResponseEntity
     */
    @PatchMapping("/{orderId}/received")
    public ResponseEntity<ApiResponse<Void>> receivedOrder(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long orderId
    ) {

        Long userId = tokenUserId.getTokenUserId(authHeader);
        orderService.receivedOrder(userId, orderId);

        return new ResponseEntity<>(new ApiResponse<>("주문 수락 완료"), HttpStatus.OK);
    }

    /**
     * 배달 여부
     *
     * @param authHeader 로그인 유저 정보
     * @param orderId
     * @return ResponseEntity
     */
    @PatchMapping("/{orderId}/delivery")
    public ResponseEntity<ApiResponse<Void>> deliveryOrder(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long orderId
    ) {
        Long userId = tokenUserId.getTokenUserId(authHeader);
        orderService.deliveryOrder(userId, orderId);

        return new ResponseEntity<>(new ApiResponse<>("배달 완료"), HttpStatus.OK);
    }

    /**
     * 주문 삭제
     *
     * @param authHeader 로그인 유저 정보
     * @param orderId 주문 정보
     * @return ResponseEntity
     */
    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponse<Void>> deleteOrder(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long orderId
    ) {
        Long userId = tokenUserId.getTokenUserId(authHeader);
        orderService.deleteOrder(userId, orderId);

        return new ResponseEntity<>(new ApiResponse<>("주문 취소"), HttpStatus.OK);
    }
}
