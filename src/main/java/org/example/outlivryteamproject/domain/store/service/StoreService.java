package org.example.outlivryteamproject.domain.store.service;

import org.example.outlivryteamproject.domain.store.dto.request.StoreRequsetDto;
import org.example.outlivryteamproject.domain.store.dto.response.StoreResponseDto;
import org.example.outlivryteamproject.domain.store.dto.response.findOneStoreResponseDto;
import org.springframework.data.domain.Page;

public interface StoreService {

    StoreResponseDto saveStore(StoreRequsetDto requsetDto);

    Page<StoreResponseDto> getStoreList(int page, int size, String storeName);

    findOneStoreResponseDto getOneStore(Long storeId);
}
