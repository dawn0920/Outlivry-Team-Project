package org.example.outlivryteamproject.domain.store.service;


import org.example.outlivryteamproject.domain.store.dto.response.StoreResponseDto;
import org.example.outlivryteamproject.domain.store.dto.response.FindOneStoreResponseDto;
import org.springframework.data.domain.Page;

public interface StoreService {

    Page<StoreResponseDto> findStoreList(int page, int size, String storeName);

    FindOneStoreResponseDto findOneStore(Long storeId);
}
