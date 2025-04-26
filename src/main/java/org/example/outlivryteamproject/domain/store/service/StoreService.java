package org.example.outlivryteamproject.domain.store.service;


import org.example.outlivryteamproject.domain.store.dto.response.StoreResponseDto;
import org.example.outlivryteamproject.domain.store.dto.response.findOneStoreResponseDto;
import org.springframework.data.domain.Page;

public interface StoreService {

    Page<StoreResponseDto> findStoreList(int page, int size, String storeName);

    findOneStoreResponseDto findOneStore(Long storeId);
}
