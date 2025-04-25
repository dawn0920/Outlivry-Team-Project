package org.example.outlivryteamproject.domain.menu.controller;


import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.example.outlivryteamproject.common.TokenUserId;
import org.example.outlivryteamproject.common.response.ApiResponse;
import org.example.outlivryteamproject.domain.menu.dto.requestDto.MenuRequestDto;
import org.example.outlivryteamproject.domain.menu.dto.responseDto.MenuResponseDto;
import org.example.outlivryteamproject.domain.menu.service.MenuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/owner/stores")
public class OwnerMenuController {

    private final MenuService menuService;

    private final TokenUserId tokenUserId;

    // 메뉴 등록
    @PostMapping("/{storeId}/menus")
    public ResponseEntity<ApiResponse<MenuResponseDto>> createMenu(
            @PathVariable("storeId") Long storeId,
            @ModelAttribute MenuRequestDto menuRequestDto,
            @RequestHeader("Authorization") String authHeader
    ){

        // 로그인 정보 가져오기

        Long userId = tokenUserId.getTokenUserId(authHeader);

        // createMenu 매서드 실행
        MenuResponseDto menuResponseDto = menuService.createMenu(storeId, userId, menuRequestDto);


        return new ResponseEntity<>( new ApiResponse<>("등록 성공", menuResponseDto), HttpStatus.CREATED);
    }


    // 메뉴 수정
    @PatchMapping("/{storeId}/menus/{menuId}")
    public ResponseEntity<ApiResponse<MenuResponseDto>> modifiedMenu(
            @PathVariable("storeId") Long storeId,
            @PathVariable("menuId") Long menuId,
            @ModelAttribute MenuRequestDto menuRequestDto,
            @RequestHeader("Authorization") String authHeader
    ){

        // 로그인 정보 가져오기
        Long userId = tokenUserId.getTokenUserId(authHeader);


        // createMenu 매서드 실행
        MenuResponseDto menuResponseDto = menuService.modifiedMenu(storeId, userId, menuRequestDto, menuId);


        return new ResponseEntity<>(new ApiResponse<>("수정 완료", menuResponseDto), HttpStatus.OK);
    }

    @PatchMapping("/{storeId}/menus/{menuId}/status")
    public ResponseEntity<ApiResponse<Void>> changeMenuStatus(
            @PathVariable("storeId") Long storeId,
            @PathVariable("menuId") Long menuId,
            @RequestHeader("Authorization") String authHeader
    ){

        // 로그인 정보 가져오기
        Long userId = tokenUserId.getTokenUserId(authHeader);

        // 상태 전환
        menuService.changeMenuStatus(storeId, userId, menuId);


        return new ResponseEntity<>(new ApiResponse<>(""), HttpStatus.OK);
    }


    // 메뉴 삭제
    @DeleteMapping("/{storeId}/menus/{menuId}")
    public ResponseEntity<ApiResponse<Void>> deleteMenu(
            @PathVariable("storeId") Long storeId,
            @PathVariable("menuId") Long menuId,
            @RequestHeader("Authorization") String authHeader
    ){

        // 로그인 정보 가져오기
        Long userId = tokenUserId.getTokenUserId(authHeader);


        // createMenu 매서드 실행
        menuService.deleteMenu(userId, storeId, menuId);

        return new ResponseEntity<>(new ApiResponse<>("삭제 완료"), HttpStatus.OK);
    }
}
