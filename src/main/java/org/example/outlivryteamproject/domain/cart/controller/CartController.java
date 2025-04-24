package org.example.outlivryteamproject.domain.cart.controller;

import lombok.RequiredArgsConstructor;
import org.example.outlivryteamproject.common.response.ApiResponse;
import org.example.outlivryteamproject.domain.cart.dto.responseDto.FindCartResponseDto;
import org.example.outlivryteamproject.domain.cart.dto.responseDto.SaveCartResponseDto;
import org.example.outlivryteamproject.domain.cart.service.CartServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartServiceImpl cartService;

    @PostMapping("/user/{userId}/menu/{menuId}")
    public ResponseEntity<SaveCartResponseDto> saveCart(
            @PathVariable Long userId,
            @PathVariable Long menuId
    ) {
        SaveCartResponseDto savedCart = cartService.save(userId, menuId);

        return new ResponseEntity<>(savedCart, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<FindCartResponseDto>>> findByUserId(
            @PathVariable Long userId
    ) {
        List<FindCartResponseDto> carts = cartService.findCart(userId);

        return new ResponseEntity<>(new ApiResponse<>("조회성공", carts), HttpStatus.OK);
    }
}
