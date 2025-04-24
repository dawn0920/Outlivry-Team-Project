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

    @PostMapping("/users/{userId}/menus/{menuId}")
    public ResponseEntity<ApiResponse<SaveCartResponseDto>> saveCart(
            @PathVariable Long userId,
            @PathVariable Long menuId
    ) {
        SaveCartResponseDto savedCart = cartService.save(userId, menuId);

        return new ResponseEntity<>(new ApiResponse<>("장바구니에 추가했습니다",savedCart), HttpStatus.CREATED);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ApiResponse<List<FindCartResponseDto>>> findByUserId(
            @PathVariable Long userId
    ) {
        List<FindCartResponseDto> carts = cartService.findCart(userId);

        return new ResponseEntity<>(new ApiResponse<>("조회성공", carts), HttpStatus.OK);
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<ApiResponse<Void>> removeCartItem(@PathVariable Long cartId) {

        cartService.removeCartItem(cartId);

        return new ResponseEntity<>(new ApiResponse<>("해당 물건을 삭제했습니다"), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<ApiResponse<Void>> removeAllCart(@PathVariable Long userId) {

        cartService.removeCart(userId);

        return new ResponseEntity<>(new ApiResponse<>("장바구니를 비웠습니다."), HttpStatus.NO_CONTENT);
    }
}
