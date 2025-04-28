package org.example.outlivryteamproject.domain.menu.service;

import com.amazonaws.services.s3.model.PutObjectRequest;
import org.example.outlivryteamproject.common.S3ImageUploader;
import org.example.outlivryteamproject.domain.menu.dto.requestDto.MenuRequestDto;
import org.example.outlivryteamproject.domain.menu.dto.requestDto.ModifiedMenuRequestDto;
import org.example.outlivryteamproject.domain.menu.dto.responseDto.MenuResponseDto;
import org.example.outlivryteamproject.domain.menu.entity.Menu;
import org.example.outlivryteamproject.domain.menu.repository.MenuRepository;
import org.example.outlivryteamproject.domain.store.entity.Store;
import org.example.outlivryteamproject.domain.store.repository.StoreRepository;
import org.example.outlivryteamproject.domain.user.entity.User;
import org.example.outlivryteamproject.domain.user.enums.UserRole;
import org.example.outlivryteamproject.exception.CustomException;
import org.example.outlivryteamproject.exception.ExceptionCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MenuServiceImplTest {

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private S3ImageUploader s3ImageUploader;

    @InjectMocks
    private MenuServiceImpl menuService;

    private User user;
    private Store store;
    private MenuRequestDto menuRequestDto;
    private ModifiedMenuRequestDto modifiedMenuRequestDto;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // User 객체 초기화
        user = new User("test@example.com", "password123", "Test User", "010-1234-5678", "1990-01-01", UserRole.USER);
        user.setId(1L);

        // Store 객체 초기화
        store = new Store();
        store.setStoreName("Test Store");
        store.setUser(user);
        store.setStoreId(1L);
        store.setPhone("010-1234-5678");
        store.setAddress("Test Address");
        store.setContent("Test Store Content");
        store.setCategory("Food");
        store.setMinDeliveryPrice(1000L);
        store.setDeliveryTip(200L);
        store.setOpenTime(LocalTime.of(9, 0));
        store.setCloseTime(LocalTime.of(21, 0));

        // MenuRequestDto 객체 초기화
        menuRequestDto = new MenuRequestDto();
        menuRequestDto.setMenuName("Test Menu");
        menuRequestDto.setPrice(2000);
        menuRequestDto.setImage(null); // 예시로 이미지 null

        MockMultipartFile mockFile = new MockMultipartFile(
                "image",
                "sample.jpg",
                "image/jpeg",
                "fake image content".getBytes()
        );
        // ModifiedMenuRequestDto 객체 초기화
        modifiedMenuRequestDto = new ModifiedMenuRequestDto();
        modifiedMenuRequestDto.setMenuName("Updated Menu");
        modifiedMenuRequestDto.setPrice(2500);
        modifiedMenuRequestDto.setImage(mockFile); // 예시로 이미지 null
    }

    @Test
    void createMenu_ShouldCreateMenu() {
        // Given
        Long storeId = 1L;
        Long userId = 1L;
        String imageUrl = "http://test.com/image.jpg";
        Menu menu = new Menu(menuRequestDto, imageUrl);
        menu.setStore(store);
        when(s3ImageUploader.uploadImage(any())).thenReturn(imageUrl);
        when(storeRepository.findByStoreIdOrElseThrow(store.getStoreId())).thenReturn(store);
        when(menuRepository.save(any(Menu.class))).thenReturn(menu);

        // When
        MenuResponseDto responseDto = menuService.createMenu(storeId, userId, menuRequestDto);

        // Then
        assertNotNull(responseDto);
        assertEquals("Test Menu", responseDto.getMenuName());
        assertEquals(2000, responseDto.getPrice());
        assertEquals(imageUrl, responseDto.getImageUrl());
    }

    @Test
    void modifiedMenu() {
        Long storeId = 1L;
        Long userId = 1L;
        Long menuId = 1L;
        String imageUrl = "http://test.com/image.jpg";
        Menu menu = new Menu(menuRequestDto, imageUrl);
        menu.setStore(store);
        when(s3ImageUploader.uploadImage(any())).thenReturn(imageUrl);
        when(storeRepository.findByStoreIdOrElseThrow(store.getStoreId())).thenReturn(store);
        when(menuRepository.findMenuByIdOrElseThrow(any(Long.class))).thenReturn(menu);

        // When
        MenuResponseDto responseDto = menuService.modifiedMenu(storeId, userId, modifiedMenuRequestDto, menuId);

        // Then
        assertNotNull(responseDto);
        assertEquals("Updated Menu", responseDto.getMenuName());
        assertEquals(2500, responseDto.getPrice());
        assertEquals(imageUrl, responseDto.getImageUrl());
    }

    @Test
    void modifiedMenu_ex() {
        Long storeId = 1L;
        Long userId = 1L;
        Long menuId = 1L;
        String imageUrl = "http://test.com/image.jpg";
        Menu menu = new Menu(menuRequestDto, imageUrl);
        menu.setStore(null);
        when(s3ImageUploader.uploadImage(any())).thenReturn(imageUrl);
        when(storeRepository.findByStoreIdOrElseThrow(store.getStoreId())).thenReturn(store);
        when(menuRepository.findMenuByIdOrElseThrow(any(Long.class))).thenReturn(menu);

        // When
        CustomException exception = assertThrows(CustomException.class, () -> {
            menuService.modifiedMenu(storeId, userId, modifiedMenuRequestDto, menuId);
        });

        assertEquals("가게에는 해당 메뉴가 없습니다.", exception.getMessage());
    }

    @Test
    void matches_ex() {
        Long storeId = 1L;
        Long userId = 3L;
        Long menuId = 1L;
        String imageUrl = "http://test.com/image.jpg";
        Menu menu = new Menu(menuRequestDto, imageUrl);
        menu.setStore(null);
        when(s3ImageUploader.uploadImage(any())).thenReturn(imageUrl);
        when(storeRepository.findByStoreIdOrElseThrow(store.getStoreId())).thenReturn(store);
        when(menuRepository.findMenuByIdOrElseThrow(any(Long.class))).thenReturn(menu);

        // When
        CustomException exception = assertThrows(CustomException.class, () -> {
            menuService.modifiedMenu(storeId, userId, modifiedMenuRequestDto, menuId);
        });

        assertEquals("해당 가게의 사장이 아닙니다.", exception.getMessage());
    }

    @Test
    void deleteMenu() {
        Long storeId = 1L;
        Long userId = 1L;
        Long menuId = 1L;
        String imageUrl = "http://test.com/image.jpg";
        Menu menu = new Menu(menuRequestDto, imageUrl);
        menu.setStore(store);
        when(s3ImageUploader.uploadImage(any())).thenReturn(imageUrl);
        when(storeRepository.findByStoreIdOrElseThrow(store.getStoreId())).thenReturn(store);
        when(menuRepository.findMenuByIdOrElseThrow(any(Long.class))).thenReturn(menu);

        // When
        menuService.deleteMenu(userId, storeId, menuId);

        // Then
        verify(menuRepository, times(1)).delete(any(Menu.class));
    }

    @Test
    void delete_ex() {
        Long storeId = 1L;
        Long userId = 1L;
        Long menuId = 1L;
        String imageUrl = "http://test.com/image.jpg";
        Menu menu = new Menu(menuRequestDto, imageUrl);
        menu.setStore(null);
        when(s3ImageUploader.uploadImage(any())).thenReturn(imageUrl);
        when(storeRepository.findByStoreIdOrElseThrow(store.getStoreId())).thenReturn(store);
        when(menuRepository.findMenuByIdOrElseThrow(any(Long.class))).thenReturn(menu);

        // When
        CustomException exception = assertThrows(CustomException.class, () -> {
            menuService.deleteMenu(storeId, userId, menuId);
        });

        assertEquals("가게에는 해당 메뉴가 없습니다.", exception.getMessage());
    }

    @Test
    void findAllMenusByStore() {
        Long storeId = 1L;
        String imageUrl = "http://test.com/image.jpg";
        Menu menu1 = new Menu(menuRequestDto, imageUrl);
        menu1.setStore(store);
        Menu menu2 = new Menu(menuRequestDto, imageUrl);
        menu2.setStore(store);
        List<Menu> menus = new ArrayList<>();
        menus.add(menu1);
        menus.add(menu2);

        when(menuRepository.findAllByStore(store)).thenReturn(menus);
        when(storeRepository.findByStoreIdOrElseThrow(storeId)).thenReturn(store);

        List<MenuResponseDto> responseDtoList = menuService.findAllMenusByStore(storeId);

        verify(menuRepository, times(1)).findAllByStore(store);
        assertEquals(responseDtoList.get(0).getMenuId(), new MenuResponseDto(menu1).getMenuId());
        assertEquals(responseDtoList.get(1).getMenuId(), new MenuResponseDto(menu2).getMenuId());
    }

    @Test
    void changeMenuStatus() {
        Long storeId = 1L;
        Long userId = 1L;
        Long menuId = 1L;
        String imageUrl = "http://test.com/image.jpg";
        Menu menu = new Menu(menuRequestDto, imageUrl);
        menu.setStore(store);
        menu.setSoldOut(false);
        when(storeRepository.findByStoreIdOrElseThrow(store.getStoreId())).thenReturn(store);
        when(menuRepository.findMenuByIdOrElseThrow(any(Long.class))).thenReturn(menu);

        // When
        menuService.changeMenuStatus(storeId, userId, menuId);

        // Then
        assertTrue(menu.isSoldOut());
        verify(menuRepository, times(1)).findMenuByIdOrElseThrow(1L);
    }

    @Test
    void changeMenuStatus_ex() {
        Long storeId = 1L;
        Long userId = 1L;
        Long menuId = 1L;
        String imageUrl = "http://test.com/image.jpg";
        Menu menu = new Menu(menuRequestDto, imageUrl);
        menu.setStore(null);
        when(storeRepository.findByStoreIdOrElseThrow(store.getStoreId())).thenReturn(store);
        when(menuRepository.findMenuByIdOrElseThrow(any(Long.class))).thenReturn(menu);

        // When
        CustomException exception = assertThrows(CustomException.class, () -> {
            menuService.changeMenuStatus(storeId, userId, menuId);
        });

        assertEquals("가게에는 해당 메뉴가 없습니다.", exception.getMessage());
    }
}