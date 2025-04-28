package org.example.outlivryteamproject.domain.store.service;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import org.example.outlivryteamproject.domain.menu.entity.Menu;
import org.example.outlivryteamproject.domain.review.repository.ReviewRepository;
import org.example.outlivryteamproject.domain.store.dto.response.FindOneStoreResponseDto;
import org.example.outlivryteamproject.domain.store.dto.response.StoreResponseDto;
import org.example.outlivryteamproject.domain.store.entity.Store;
import org.example.outlivryteamproject.domain.store.repository.StoreRepository;
import org.example.outlivryteamproject.domain.user.entity.User;
import org.example.outlivryteamproject.exception.CustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@ExtendWith(MockitoExtension.class)
class StoreServiceImplTest {

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private StoreServiceImpl storeService;

    @Test
    @DisplayName("가게 목록 조회 성공")
    void findStoreList_success_to_storeList() {
        // Given
        Pageable pageable = PageRequest.of(0,5, Sort.by(Direction.DESC, "createTime"));

        Store store1 = mock(Store.class);
        Store store2 = mock(Store.class);
        User user1 = mock(User.class);
        User user2 = mock(User.class);

        // When
        when(store1.getStoreName()).thenReturn("가게 이름1");
        when(store2.getStoreName()).thenReturn("가게 이름2");
        when(user1.getId()).thenReturn(1L);
        when(user2.getId()).thenReturn(2L);
        when(store1.getUser()).thenReturn(user1);
        when(store2.getUser()).thenReturn(user2);

        Page<Store> storePage = new PageImpl<>(List.of(store1,store2));

        when(storeRepository.findAll(pageable)).thenReturn(storePage);

        Page<StoreResponseDto> result = storeService.findStoreList(0,5,null);

        // Then
        assertNotNull(result);
        assertEquals(2,result.getTotalElements());
        assertEquals("가게 이름1", result.getContent().get(0).getStoreName());
        assertEquals("가게 이름2", result.getContent().get(1).getStoreName());
    }

    @Test
    @DisplayName("가게 이름으로 조회 성공")
    void findStoreList_success_to_storeName() {
        // Given
        String storeName = "가게 이름";
        Pageable pageable = PageRequest.of(0,5, Sort.by(Direction.DESC, "createTime"));

        Store store = mock(Store.class);
        User user = mock(User.class);

        // When
        when(store.getStoreName()).thenReturn(storeName);
        when(store.getUser()).thenReturn(user);
        when(user.getId()).thenReturn(1L);

        Page<Store> storePage = new PageImpl<>(List.of(store));

        when(storeRepository.findByStoreNameContainingOrElseThrow(storeName, pageable)).thenReturn(storePage);

        Page<StoreResponseDto> result = storeService.findStoreList(0,5,storeName);

        // Then
        assertNotNull(result);
        assertEquals(1,result.getTotalElements());
        assertEquals("가게 이름", result.getContent().get(0).getStoreName());
    }

    @Test
    @DisplayName("가게 단건 조회 성공")
    void findOneStore_success() {
        // Given
        Long storeId = 1L;
        Double stars = 3.55;
        Double expectedstars = 3.5;
        Long userId = 1L;

        User user = new User();
        user.setId(userId);

        Store store = mock(Store.class);
        Menu menu1 = mock(Menu.class);
        Menu menu2 = mock(Menu.class);

        // When
        when(store.getStoreId()).thenReturn(storeId);
        when(store.getStoreName()).thenReturn("가게 이름");
        when(store.getUser()).thenReturn(user);

        when(menu1.getMenuName()).thenReturn("첫번째 메뉴");
        when(menu1.getStore()).thenReturn(store);

        when(menu2.getMenuName()).thenReturn("두번째 메뉴");
        when(menu2.getStore()).thenReturn(store);

        when(store.getMenuList()).thenReturn(Arrays.asList(menu1, menu2));

        when(storeRepository.findByStoreIdWithMenuListOrElseThrow(storeId)).thenReturn(store);
        when(reviewRepository.findAverageRatingByStoreId(storeId)).thenReturn(stars);

        FindOneStoreResponseDto result = storeService.findOneStore(storeId);

        // Then
        assertNotNull(result);
        assertEquals("가게 이름",result.getStoreName());
        assertEquals("첫번째 메뉴", result.getMenuList().get(0).getMenuName());
        assertEquals("두번째 메뉴", result.getMenuList().get(1).getMenuName());
        assertEquals(expectedstars, result.getStars());
    }

    @Test
    @DisplayName("가게 단건 조회 실패")
    void findOneStore_not_found() {
        // Given

        // When
        CustomException exception = assertThrows(
            CustomException.class,
            () -> storeService.findOneStore(null)
        );

        // Then
        assertEquals("해당 가게를 찾을 수 없습니다.",exception.getMessage());
    }
}