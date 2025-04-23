package org.example.outlivryteamproject.domain.menu.service;

import org.example.outlivryteamproject.domain.menu.dto.requestDto.MenuRequestDto;
import org.example.outlivryteamproject.domain.menu.dto.responseDto.MenuResponseDto;

public interface MenuService {
    MenuResponseDto createMenu(MenuRequestDto menuRequestDto);
}
