package org.example.outlivryteamproject.domain.store.service;

import org.example.outlivryteamproject.domain.store.dto.request.StoreRequestDto;
import org.example.outlivryteamproject.domain.store.dto.request.updateStoreRequestDto;
import org.example.outlivryteamproject.domain.store.dto.response.StoreResponseDto;
import org.example.outlivryteamproject.domain.store.dto.response.findOneStoreResponseDto;
import org.springframework.data.domain.Page;

public interface StoreService {

    StoreResponseDto saveStore(StoreRequestDto requsetDto);

    Page<StoreResponseDto> findStoreList(int page, int size, String storeName);

    findOneStoreResponseDto findOneStore(Long storeId);

    StoreResponseDto updateStore(Long storeId, updateStoreRequestDto requsetDto);

    void deleteStore(Long storeId);
}
