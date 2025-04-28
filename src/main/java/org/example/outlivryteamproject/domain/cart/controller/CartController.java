package org.example.outlivryteamproject.domain.cart.controller;

import lombok.RequiredArgsConstructor;
import org.example.outlivryteamproject.common.TokenUserId;
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
    private final TokenUserId tokenUserId;

    /**
     * 장바구니 생성
     *
     * @param authHeader 로그인 유저 정보
     * @param menuId 추가할 메뉴
     * @return ResponseEntity
     */
    @PostMapping("/menus/{menuId}")
    public ResponseEntity<ApiResponse<SaveCartResponseDto>> saveCart(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable("menuId") Long menuId
    ) {
        Long userId = tokenUserId.getTokenUserId(authHeader);
        SaveCartResponseDto savedCart = cartService.save(userId, menuId);

        return new ResponseEntity<>(new ApiResponse<>("장바구니에 추가했습니다",savedCart), HttpStatus.CREATED);
    }

    /**
     * 장바구니 조회
     *
     * @param authHeader 로그인 유저 정보
     * @return ResponseEntity
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<FindCartResponseDto>>> findByUserId(
            @RequestHeader("Authorization") String authHeader
    ) {
        Long userId = tokenUserId.getTokenUserId(authHeader);
        List<FindCartResponseDto> carts = cartService.findCart(userId);

        return new ResponseEntity<>(new ApiResponse<>("조회성공", carts), HttpStatus.OK);
    }

    /**
     * 특정 장바구니 삭제
     *
     * @param authHeader 로그인 유저 정보
     * @param cartId 삭제하려는 장바구니
     * @return ResponseEntity
     */
    @DeleteMapping("/{cartId}")
    public ResponseEntity<ApiResponse<Void>> removeCartItem(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long cartId
    ) {

        Long userId = tokenUserId.getTokenUserId(authHeader);
        cartService.removeCartItem(userId, cartId);

        return new ResponseEntity<>(new ApiResponse<>("삭제 완료"), HttpStatus.OK);
    }

    /**
     * 전체 메뉴 삭제
     *
     * @param authHeader 로그인 유저 정보
     * @return ResponseEntity
     */
    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> removeAllCart(@RequestHeader("Authorization") String authHeader) {

        Long userId = tokenUserId.getTokenUserId(authHeader);
        cartService.removeCart(userId);

        return new ResponseEntity<>(new ApiResponse<>("장바구니를 비웠습니다."), HttpStatus.OK);
    }
}
