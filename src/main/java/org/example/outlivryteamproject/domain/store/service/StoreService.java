package org.example.outlivryteamproject.domain.store.service;

import org.example.outlivryteamproject.domain.store.dto.request.SaveStoreRequsetDto;
import org.example.outlivryteamproject.domain.store.dto.response.SaveStoreResponseDto;
import org.springframework.data.domain.Page;

public interface StoreService {

    SaveStoreResponseDto saveStore(SaveStoreRequsetDto requsetDto);

    Page<SaveStoreResponseDto> getStoreList(int page, int size, String storeName);
}
