package org.example.outlivryteamproject.domain.menu.service;

import org.example.outlivryteamproject.domain.menu.dto.requestDto.MenuRequestDto;
import org.example.outlivryteamproject.domain.menu.dto.responseDto.MenuResponseDto;

public interface MenuService {
    MenuResponseDto createMenu(MenuRequestDto menuRequestDto);

    MenuResponseDto modifiedMenu(MenuRequestDto menuRequestDto, Long menuId);

    MenuResponseDto deleteMenu(Long userId, Long storeId, Long menuId);

    MenuResponseDto findMenuById(Long menuId);
}
