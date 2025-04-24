package org.example.outlivryteamproject.domain.cart.service;

import org.example.outlivryteamproject.domain.cart.dto.responseDto.FindCartResponseDto;
import org.example.outlivryteamproject.domain.cart.dto.responseDto.SaveCartResponseDto;

import java.util.List;

public interface CartService {

    SaveCartResponseDto save(Long userId, Long menuId);

    List<FindCartResponseDto> findCart(Long userId);

    void removeCartItem(Long cartId);

    void removeCart(Long userId);
}
