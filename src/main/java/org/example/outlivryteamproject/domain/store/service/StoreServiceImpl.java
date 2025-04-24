package org.example.outlivryteamproject.domain.store.service;


import lombok.RequiredArgsConstructor;
import org.example.outlivryteamproject.domain.store.dto.response.StoreResponseDto;
import org.example.outlivryteamproject.domain.store.dto.response.findOneStoreResponseDto;
import org.example.outlivryteamproject.domain.store.entity.Store;
import org.example.outlivryteamproject.domain.store.repository.StoreRepository;
import org.example.outlivryteamproject.exception.CustomException;
import org.example.outlivryteamproject.exception.ExceptionCode;
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
    public Page<StoreResponseDto> findStoreList(int page, int size, String storeName) {

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
    public findOneStoreResponseDto findOneStore(Long storeId) {

        if(storeId == null) {
            throw new CustomException(ExceptionCode.STORE_NOT_FOUND);
        }

        Store store = storeRepository.findByStoreIdWithMenuListOrElseThrow(storeId);

        return new findOneStoreResponseDto(store);
    }


}
