package org.example.outlivryteamproject.domain.store.service;

import org.example.outlivryteamproject.domain.store.dto.request.StoreRequestDto;
import org.example.outlivryteamproject.domain.store.dto.request.updateStoreRequestDto;
import org.example.outlivryteamproject.domain.store.dto.response.StoreResponseDto;

public interface StoreOwnerService {

    StoreResponseDto saveStore(StoreRequestDto requsetDto, Long userId);

    StoreResponseDto updateStore(Long storeId, updateStoreRequestDto requsetDto,Long userId);

    void deleteStore(Long storeId, Long userId);
}
