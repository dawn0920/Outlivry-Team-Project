package org.example.outlivryteamproject.domain.cart.service;

import org.example.outlivryteamproject.domain.cart.dto.responseDto.FindCartByUserIdResponseDto;
import org.example.outlivryteamproject.domain.cart.dto.responseDto.SaveCartResponseDto;
import org.springframework.data.domain.Page;

public interface CartService {

    SaveCartResponseDto save(Long userId, Long menuId);

    Page<FindCartByUserIdResponseDto> findCart(Long userId, int page);
}
