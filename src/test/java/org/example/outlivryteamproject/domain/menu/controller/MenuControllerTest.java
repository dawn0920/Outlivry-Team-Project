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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MenuController.class)
class MenuControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    MenuService menuService;

    @MockitoBean
    private TokenUserId tokenUserId;

    @MockitoBean
    private JpaMetamodelMappingContext jpaMetamodelMappingContext;


    @Test
    void findAllMenusByStore() throws Exception{
        Long storeId = 1L;
        String authHeader = "jwtUtil.createToken(userId, UserRole.OWNER)";
        List<MenuResponseDto> findMenuList = new ArrayList<>();

        // TokenUserId를 Mock하여 인증된 사용자 ID를 반환하도록 설정
        doReturn(findMenuList).when(menuService).findAllMenusByStore(eq(storeId));

        // JwtFilter가 Mock이므로 필터가 동작하지 않고 바로 Controller 테스트 진행
        mockMvc.perform(multipart("/stores/{storeId}/menus", storeId)
                        .header("Authorization", authHeader)
                        .with(request -> { // 여기 추가
                            request.setMethod("GET");
                            return request;
                        }))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(equalTo("조회 성공")));
    }
}