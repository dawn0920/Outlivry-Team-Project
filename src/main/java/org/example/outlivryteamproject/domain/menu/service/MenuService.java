package org.example.outlivryteamproject.domain.menu.service;

import org.example.outlivryteamproject.domain.menu.dto.requestDto.MenuRequestDto;
import org.example.outlivryteamproject.domain.menu.dto.requestDto.ModifiedMenuRequestDto;
import org.example.outlivryteamproject.domain.menu.dto.responseDto.MenuResponseDto;

import java.util.List;

public interface MenuService {
    MenuResponseDto createMenu(Long storeId, Long userId, MenuRequestDto menuRequestDto);

    MenuResponseDto modifiedMenu(Long storeId, Long userId, ModifiedMenuRequestDto modifiedMenuRequestDto, Long menuId);

    void deleteMenu(Long userId, Long storeId, Long menuId);

    MenuResponseDto findMenuById(Long menuId);

    List<MenuResponseDto> findAllMenusByStore(Long storeId);

    void changeMenuStatus(Long storeId, Long userId, Long menuId);
}
