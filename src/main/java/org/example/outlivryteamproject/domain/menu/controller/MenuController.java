package org.example.outlivryteamproject.domain.menu.controller;

import lombok.RequiredArgsConstructor;
import org.example.outlivryteamproject.common.response.ApiResponse;
import org.example.outlivryteamproject.domain.menu.dto.responseDto.MenuResponseDto;
import org.example.outlivryteamproject.domain.menu.service.MenuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/stores")
public class MenuController {

    private final MenuService menuService;

    // 메뉴 단건 조회
    @GetMapping("/menus/{menuId}")
    public ResponseEntity<ApiResponse<MenuResponseDto>> findMenuById(@PathVariable("menuId") Long menuId){

        MenuResponseDto findMenuById = menuService.findMenuById(menuId);

        return new ResponseEntity<>( new ApiResponse<>("조회 성공", findMenuById), HttpStatus.OK);
    }

    // 메뉴 가게별 조회
    @GetMapping("/{storeId}/menus")
    public ResponseEntity<ApiResponse<List<MenuResponseDto>>> findAllMenusByStore(@PathVariable("storeId") Long storeId){

        List<MenuResponseDto> findMenuList = menuService.findAllMenusByStore(storeId);

        return new ResponseEntity<>( new ApiResponse<>("조회 성공", findMenuList), HttpStatus.OK);

    }
}
