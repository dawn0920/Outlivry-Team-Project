package org.example.outlivryteamproject.domain.store.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.example.outlivryteamproject.common.S3ImageUploader;
import org.example.outlivryteamproject.domain.store.dto.request.StoreRequestDto;
import org.example.outlivryteamproject.domain.store.dto.request.UpdateStoreRequestDto;
import org.example.outlivryteamproject.domain.store.dto.response.StoreResponseDto;
import org.example.outlivryteamproject.domain.store.entity.Store;
import org.example.outlivryteamproject.domain.store.repository.StoreRepository;
import org.example.outlivryteamproject.domain.user.entity.User;
import org.example.outlivryteamproject.domain.user.repository.UserRepository;
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
        requestDto.setStoreName("상호명1");
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
        assertThat(storeResponseDto.getStoreName()).isEqualTo("상호명1");
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

//    @Test
//    void deleteStore() {
//    }
}