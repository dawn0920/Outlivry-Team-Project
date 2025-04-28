package org.example.outlivryteamproject.domain.store.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import org.example.outlivryteamproject.common.S3ImageUploader;
import org.example.outlivryteamproject.domain.menu.entity.Menu;
import org.example.outlivryteamproject.domain.store.dto.request.StoreRequestDto;
import org.example.outlivryteamproject.domain.store.dto.request.UpdateStoreRequestDto;
import org.example.outlivryteamproject.domain.store.dto.response.StoreResponseDto;
import org.example.outlivryteamproject.domain.store.entity.Store;
import org.example.outlivryteamproject.domain.store.repository.StoreRepository;
import org.example.outlivryteamproject.domain.user.entity.User;
import org.example.outlivryteamproject.domain.user.repository.UserRepository;
import org.example.outlivryteamproject.exception.CustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
class StoreOwnerServiceImplTest {

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private S3ImageUploader s3ImageUploader;

    @InjectMocks
    private StoreOwnerServiceImpl storeOwnerService;

    @Test
    @DisplayName("가게 생성 성공시 가게 이름을 확인한다.")
    void saveStore_success() {
        // Given
        StoreRequestDto requestDto = new StoreRequestDto();
        requestDto.setStoreName("테스트 가게");
        requestDto.setStorePicture(mock(MultipartFile.class));

        User user = new User();
        user.setId(1L);

        // When
        when(userRepository.findByIdOrElseThrow(anyLong())).thenReturn(user);
        when(s3ImageUploader.uploadImage(any(MultipartFile.class))).thenReturn("Url");
        when(storeRepository.save(any(Store.class))).thenAnswer(invocation -> invocation.getArgument(0));

        StoreResponseDto storeResponseDto = storeOwnerService.saveStore(requestDto, user.getId());

        // Then
        assertThat(storeResponseDto).isNotNull();
        assertThat(storeResponseDto.getStoreName()).isEqualTo("테스트 가게");
    }

    @Test
    @DisplayName("가게 생성시 중복된 이름 사용 불가")
    void saveStore_dont_create_same_storeName() {
        // Given
        StoreRequestDto requestDto = new StoreRequestDto();
        requestDto.setStoreName("테스트 가게");

        Long userId = 1L;

        given(storeRepository.existsByStoreName("테스트 가게")).willReturn(true);

        // When
        CustomException exception = assertThrows(
            CustomException.class,
            () -> storeOwnerService.saveStore(requestDto, userId)
        );

        // Then
        assertEquals("이미 존재하는 가게 이름입니다.", exception.getMessage());
    }

    @Test
    @DisplayName("가게 생성시 사장 한명당 최대 3개의 가게를 가짐")
    void saveStore_oneOwner_limit_threeStore() {
        // Given
        StoreRequestDto requestDto = new StoreRequestDto();
        requestDto.setStoreName("4번째 가게");

        Long userId = 1L;

        given(storeRepository.countByUserId(userId)).willReturn(3L);

        // When
        CustomException exception = assertThrows(
            CustomException.class,
            () -> storeOwnerService.saveStore(requestDto, userId)
        );

        // Then
        assertEquals("가게는 3개까지 생성 가능 합니다.",exception.getMessage());
    }

    @Test
    @DisplayName("가게 이름 수정을 확인한다.")
    void updateStore_success() {
        // Given
        UpdateStoreRequestDto requestDto = new UpdateStoreRequestDto();
        requestDto.setNewStoreName("new상호명");
        requestDto.setNewStorePicture(mock(MultipartFile.class));

        User user = new User();
        user.setId(1L);

        Long storeId = 1L;
        Store store = new Store();
        store.setStoreName("old상호명");
        store.setUser(user);

        // When
        when(userRepository.findByIdOrElseThrow(anyLong())).thenReturn(user);
        when(storeRepository.findByStoreIdOrElseThrow(anyLong())).thenReturn(store);
        when(s3ImageUploader.uploadImage(any(MultipartFile.class))).thenReturn("Url");

        StoreResponseDto storeResponseDto = storeOwnerService.updateStore(storeId, requestDto,
            user.getId());
        // Then
        assertThat(storeResponseDto).isNotNull();
        assertThat(storeResponseDto.getStoreName()).isEqualTo(requestDto.getNewStoreName());
    }

    @Test
    @DisplayName("가게 수정시 해당 가게 사장이 아니면 불가능")
    void updateStore_equals_owner() {
        // Given
        UpdateStoreRequestDto requestDto = new UpdateStoreRequestDto();

        Long storeId = 1L;
        Long userId = 1L;
        Long otherUserId = 2L;

        User User = new User();
        User.setId(userId);

        Store store = new Store();
        store.setStoreId(storeId);
        store.setUser(User);

        given(storeRepository.findByStoreIdOrElseThrow(storeId)).willReturn(store);

        // When
        CustomException exception = assertThrows(
            CustomException.class,
            () -> storeOwnerService.updateStore(storeId, requestDto, otherUserId)
        );

        // Then
        assertEquals("해당 가게의 사장이 아닙니다.",exception.getMessage());
    }

    @Test
    @DisplayName("가게 삭제를 확인")
    void deleteStore_success() {
        // Given
        Long storeId = 1L;
        Long userId = 1L;

        User user = new User();
        user.setId(userId);

        Menu menu1 = mock(Menu.class);
        menu1.setMenuName("첫번째 메뉴");
        Menu menu2 = mock(Menu.class);
        menu2.setMenuName("두번째 메뉴");

        Store store = new Store();
        store.setStoreId(storeId);
        store.setUser(user);
        store.setDeleted(false);
        store.setMenuList(Arrays.asList(menu1, menu2));

        given(userRepository.findByIdOrElseThrow(userId)).willReturn(user);
        given(storeRepository.findByStoreIdOrElseThrow(storeId)).willReturn(store);

        // When
        storeOwnerService.deleteStore(storeId,userId);

        // Then
        assertTrue(store.isDeleted()); // soft delete 확인
        verify(storeRepository, times(1)).save(store); // 저장 여부 확인
        verify(storeRepository, never()).delete(any()); // 진짜 삭제는 x

        verify(menu1, times(1)).isDelete();
        verify(menu2, times(1)).isDelete();
    }

    @Test
    @DisplayName("가게 삭제시 해당 사장이 아니면 불가능")
    void deleteStore_equals_owner() {
        // Given
        Long storeId = 1L;
        Long userId = 1L;
        Long otherUserId = 2L;

        User user = new User();
        user.setId(userId);

        Store store = new Store();
        store.setStoreId(storeId);
        store.setUser(user);

        given(storeRepository.findByStoreIdOrElseThrow(storeId)).willReturn(store);

        // When
        CustomException exception = assertThrows(
            CustomException.class,
            () -> storeOwnerService.deleteStore(storeId, otherUserId)
        );

        // Then
        assertEquals("해당 가게의 사장이 아닙니다.",exception.getMessage());
    }
}