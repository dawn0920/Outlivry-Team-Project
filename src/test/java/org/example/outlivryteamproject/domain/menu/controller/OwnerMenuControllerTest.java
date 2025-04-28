package org.example.outlivryteamproject.domain.menu.controller;


import org.example.outlivryteamproject.common.TokenUserId;
import org.example.outlivryteamproject.domain.menu.dto.requestDto.MenuRequestDto;
import org.example.outlivryteamproject.domain.menu.dto.requestDto.ModifiedMenuRequestDto;
import org.example.outlivryteamproject.domain.menu.dto.responseDto.MenuResponseDto;
import org.example.outlivryteamproject.domain.menu.entity.Menu;
import org.example.outlivryteamproject.domain.menu.service.MenuService;
import org.example.outlivryteamproject.domain.store.entity.Store;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.mockito.BDDMockito.given;

@WebMvcTest(OwnerMenuController.class)
class OwnerMenuControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    MenuService menuService;

    @MockitoBean
    private TokenUserId tokenUserId;

    @MockitoBean
    private JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Test
    void createMenu() throws Exception {

        Long userId = 1L;
        Long storeId = 1L;
        String authHeader = "jwtUtil.createToken(userId, UserRole.OWNER)";

        Store store = new Store();
        MenuRequestDto menuRequestDto = new MenuRequestDto("와퍼", 8100, null);
        Menu menu = new Menu(menuRequestDto, null);
        menu.setStore(store);

        MenuResponseDto menuResponseDto = new MenuResponseDto(menu);

        // TokenUserId를 Mock하여 인증된 사용자 ID를 반환하도록 설정
        given(tokenUserId.getTokenUserId(authHeader)).willReturn(userId);
        given(menuService.createMenu(eq(storeId), eq(userId), any(MenuRequestDto.class)))
                .willReturn(menuResponseDto);

        // JwtFilter가 Mock이므로 필터가 동작하지 않고 바로 Controller 테스트 진행
        mockMvc.perform(multipart("/owners/stores/{storeId}/menus", storeId)  // post() 대신 multipart()
                        .file("image", new byte[0])  // 파일이 없더라도 multipart 요청을 강제
                        .param("menuName", "와퍼")
                        .param("price", "8100")
                        .header("Authorization", authHeader))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(equalTo("등록 성공")))
                .andExpect(jsonPath("$.contents.menuName").value(equalTo("와퍼")))
                .andExpect(jsonPath("$.contents.price").value(equalTo(8100)))
                .andExpect(jsonPath("$.contents.imageUrl").value(equalTo(null)));
    }

    @Test
    void modifiedMenu() throws Exception {

        Long userId = 1L;
        Long storeId = 1L;
        Long menuId = 1L;
        String authHeader = "jwtUtil.createToken(userId, UserRole.OWNER)";

        MenuRequestDto menuRequestDto = new MenuRequestDto("와퍼", 8900, null);

        Store store = new Store();
        Menu menu = new Menu(menuRequestDto, null);
        menu.setStore(store);

        MenuResponseDto menuResponseDto = new MenuResponseDto(menu);

        // TokenUserId를 Mock하여 인증된 사용자 ID를 반환하도록 설정
        given(tokenUserId.getTokenUserId(authHeader)).willReturn(userId);
        doReturn(menuResponseDto).when(menuService).modifiedMenu(eq(storeId), eq(userId), any(ModifiedMenuRequestDto.class), eq(menuId));

        // JwtFilter가 Mock이므로 필터가 동작하지 않고 바로 Controller 테스트 진행
        mockMvc.perform(multipart("/owners/stores/{storeId}/menus/{menuId}", storeId, menuId)
                        .file("image", new byte[0])
                        .param("price", "8900")
                        .header("Authorization", authHeader)
                        .with(request -> { // 여기 추가
                            request.setMethod("PATCH");
                            return request;
                        }))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(equalTo("수정 완료")))
                .andExpect(jsonPath("$.contents.menuName").value(equalTo("와퍼")))
                .andExpect(jsonPath("$.contents.price").value(equalTo(8900)))
                .andExpect(jsonPath("$.contents.imageUrl").value(equalTo(null)));
    }

    @Test
    void changeMenuStatus() throws Exception{
        Long userId = 1L;
        Long storeId = 1L;
        Long menuId = 1L;
        String authHeader = "jwtUtil.createToken(userId, UserRole.OWNER)";

        // TokenUserId를 Mock하여 인증된 사용자 ID를 반환하도록 설정
        given(tokenUserId.getTokenUserId(authHeader)).willReturn(userId);

        // JwtFilter가 Mock이므로 필터가 동작하지 않고 바로 Controller 테스트 진행
        mockMvc.perform(multipart("/owners/stores/{storeId}/menus/{menuId}/status", storeId, menuId)
                        .header("Authorization", authHeader)
                        .with(request -> { // 여기 추가
                            request.setMethod("PATCH");
                            return request;
                        }))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(equalTo("재고 상태 변경")))
                .andExpect(jsonPath("$.contents").value(equalTo(null)));
    }

    @Test
    void deleteMenu() throws Exception{
        Long userId = 1L;
        Long storeId = 1L;
        Long menuId = 1L;
        String authHeader = "jwtUtil.createToken(userId, UserRole.OWNER)";

        // TokenUserId를 Mock하여 인증된 사용자 ID를 반환하도록 설정
        given(tokenUserId.getTokenUserId(authHeader)).willReturn(userId);

        // JwtFilter가 Mock이므로 필터가 동작하지 않고 바로 Controller 테스트 진행
        mockMvc.perform(multipart("/owners/stores/{storeId}/menus/{menuId}", storeId, menuId)
                        .header("Authorization", authHeader)
                        .with(request -> { // 여기 추가
                            request.setMethod("DELETE");
                            return request;
                        }))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(equalTo("삭제 완료")))
                .andExpect(jsonPath("$.contents").value(equalTo(null)));
    }
}