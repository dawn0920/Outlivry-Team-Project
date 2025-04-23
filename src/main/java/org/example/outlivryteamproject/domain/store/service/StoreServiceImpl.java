package org.example.outlivryteamproject.domain.store.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.outlivryteamproject.domain.menu.dto.responseDto.MenuResponseDto;
import org.example.outlivryteamproject.domain.menu.repository.MenuRepository;
import org.example.outlivryteamproject.domain.store.dto.request.StoreRequsetDto;
import org.example.outlivryteamproject.domain.store.dto.response.StoreResponseDto;
import org.example.outlivryteamproject.domain.store.dto.response.findOneStoreResponseDto;
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
    public StoreResponseDto saveStore(StoreRequsetDto requsetDto) {

        Store store = new Store(requsetDto);
        Store savedStore = storeRepository.save(store);

        return new StoreResponseDto(savedStore);
    }

    @Override
    @Transactional
    public Page<StoreResponseDto> getStoreList(int page, int size, String storeName) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Direction.DESC, "createTime"));

        Page<Store> storePage;
        if(storeName != null && !storeName.isEmpty()) {
            storePage = storeRepository.findByStoreNameContaining(storeName, pageable);
        } else {
            storePage = storeRepository.findAll(pageable);
        }

        return storePage.map(StoreResponseDto::new);
    }

    @Override
    public findOneStoreResponseDto getOneStore(Long storeId) {
        Store store = storeRepository.findByStoreIdOrElseThrow(storeId);

        return new findOneStoreResponseDto(store);
    }
}
