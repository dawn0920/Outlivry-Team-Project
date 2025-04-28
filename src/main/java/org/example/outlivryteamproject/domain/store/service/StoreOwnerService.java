package org.example.outlivryteamproject.domain.store.service;

import org.example.outlivryteamproject.domain.store.dto.request.StoreRequestDto;
import org.example.outlivryteamproject.domain.store.dto.request.UpdateStoreRequestDto;
import org.example.outlivryteamproject.domain.store.dto.response.StoreResponseDto;

public interface StoreOwnerService {

    StoreResponseDto saveStore(StoreRequestDto requestDto, Long userId);

    StoreResponseDto updateStore(Long storeId, UpdateStoreRequestDto requestDto,Long userId);

    void deleteStore(Long storeId, Long userId);
}
