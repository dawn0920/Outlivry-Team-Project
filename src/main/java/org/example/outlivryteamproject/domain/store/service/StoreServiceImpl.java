package org.example.outlivryteamproject.domain.store.service;

import lombok.RequiredArgsConstructor;
import org.example.outlivryteamproject.domain.store.dto.request.SaveStoreRequsetDto;
import org.example.outlivryteamproject.domain.store.dto.response.SaveStoreResponseDto;
import org.example.outlivryteamproject.domain.store.entity.Store;
import org.example.outlivryteamproject.domain.store.repository.StoreRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService{

    private final StoreRepository storeRepository;

    @Override
    @Transactional
    public SaveStoreResponseDto saveStore(SaveStoreRequsetDto requsetDto) {

        Store store = new Store(requsetDto);
        Store savedStore = storeRepository.save(store);

        return new SaveStoreResponseDto(savedStore);
    }

    @Override
    public Page<SaveStoreResponseDto> getStoreList(int page, int size, String storeName) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Direction.DESC, "createTime"));

        Page<Store> storePage;
        if(storeName != null && !storeName.isEmpty()) {
            storePage = storeRepository.findByStoreNameContaining(storeName, pageable);
        } else {
            storePage = storeRepository.findAll(pageable);
        }

        return storePage.map(SaveStoreResponseDto::new);
    }
}
