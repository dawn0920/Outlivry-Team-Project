package org.example.outlivryteamproject.domain.store.service;

import lombok.RequiredArgsConstructor;
import org.example.outlivryteamproject.domain.store.dto.request.StoreRequsetDto;
import org.example.outlivryteamproject.domain.store.dto.response.StoreResponseDto;
import org.example.outlivryteamproject.domain.store.entity.Store;
import org.example.outlivryteamproject.domain.store.repository.StoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService{

    private final StoreRepository storeRepository;

    @Override
    @Transactional
    public StoreResponseDto save(StoreRequsetDto requsetDto) {

        Store store = new Store(requsetDto);
        Store savedStore = storeRepository.save(store);

        return new StoreResponseDto(savedStore);
    }
}
