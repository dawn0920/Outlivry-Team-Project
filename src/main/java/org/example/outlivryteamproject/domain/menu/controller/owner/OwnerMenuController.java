package org.example.outlivryteamproject.domain.menu.controller.owner;


import lombok.RequiredArgsConstructor;
import org.example.outlivryteamproject.common.response.ApiResponse;
import org.example.outlivryteamproject.domain.menu.dto.requestDto.MenuRequestDto;
import org.example.outlivryteamproject.domain.menu.dto.responseDto.MenuResponseDto;
import org.example.outlivryteamproject.domain.menu.service.MenuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("owner/stores")
public class OwnerMenuController {

    private final MenuService menuService;

    // 메뉴 등록
    @PostMapping("/{storeId}/menu")
    public ResponseEntity<ApiResponse<MenuResponseDto>> createMenu(@PathVariable Long storeId, @RequestBody MenuRequestDto menuRequestDto){

        // 로그인 정보 가져오기
        Long userId = 55L;

        // 로그인 정보, storeId 넣어서 createMenuRequestDto 구성
        menuRequestDto.setStoreId(storeId);
        menuRequestDto.setUserId(userId);

        // createMenu 매서드 실행
        MenuResponseDto menuResponseDto = menuService.createMenu(menuRequestDto);


        return new ResponseEntity<>( new ApiResponse<>("등록 성공", menuResponseDto), HttpStatus.CREATED);
    }


    // 메뉴 수정


    // 메뉴 삭제


    // 메뉴 상태 변경
}
