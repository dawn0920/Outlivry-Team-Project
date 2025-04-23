package org.example.outlivryteamproject.domain.cart.controller;

import lombok.RequiredArgsConstructor;
import org.example.outlivryteamproject.domain.cart.dto.responseDto.FindCartByUserIdResponseDto;
import org.example.outlivryteamproject.domain.cart.dto.responseDto.SaveCartResponseDto;
import org.example.outlivryteamproject.domain.cart.service.CartServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Page<FindCartByUserIdResponseDto>> findByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "1") int page
    ) {
        cartService.findCart(userId, page)
    }
}
