package org.example.outlivryteamproject.domain.menu.controller;


import lombok.RequiredArgsConstructor;
import org.example.outlivryteamproject.common.response.ApiResponse;
import org.example.outlivryteamproject.domain.menu.dto.requestDto.MenuRequestDto;
import org.example.outlivryteamproject.domain.menu.dto.responseDto.MenuResponseDto;
import org.example.outlivryteamproject.domain.menu.service.MenuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("owner/stores")
public class OwnerMenuController {

    private final MenuService menuService;

    // 메뉴 등록
    @PostMapping("/{storeId}/menu")
    public ResponseEntity<ApiResponse<MenuResponseDto>> createMenu(@PathVariable Long storeId, @RequestBody MenuRequestDto menuRequestDto){

        // 로그인 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.parseLong(authentication.getName());

        // 로그인 정보, storeId 넣어서 createMenuRequestDto 구성
        menuRequestDto.setStoreId(storeId);
        menuRequestDto.setUserId(userId);

        // createMenu 매서드 실행
        MenuResponseDto menuResponseDto = menuService.createMenu(menuRequestDto);


        return new ResponseEntity<>( new ApiResponse<>("등록 성공", menuResponseDto), HttpStatus.CREATED);
    }


    // 메뉴 수정
    @PatchMapping("/{storeId}/menu/{menuId}")
    public ResponseEntity<ApiResponse<MenuResponseDto>> modifiedMenu(@PathVariable Long storeId, @PathVariable Long menuId, @RequestBody MenuRequestDto menuRequestDto){

        // 로그인 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.parseLong(authentication.getName());

        // 로그인 정보, storeId 넣어서 createMenuRequestDto 구성
        menuRequestDto.setStoreId(storeId);
        menuRequestDto.setUserId(userId);

        // createMenu 매서드 실행
        MenuResponseDto menuResponseDto = menuService.modifiedMenu(menuRequestDto, menuId);


        return new ResponseEntity<>(new ApiResponse<>("수정 완료", menuResponseDto), HttpStatus.OK);
    }


    // 메뉴 삭제
    @DeleteMapping("/{storeId}/menu/{menuId}")
    public ResponseEntity<ApiResponse<MenuResponseDto>> deleteMenu(@PathVariable Long storeId, @PathVariable Long menuId){

        // 로그인 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.parseLong(authentication.getName());

        // createMenu 매서드 실행
        menuService.deleteMenu(userId, storeId, menuId);

        return new ResponseEntity<>(new ApiResponse<>("삭제 완료"), HttpStatus.OK);
    }
}
