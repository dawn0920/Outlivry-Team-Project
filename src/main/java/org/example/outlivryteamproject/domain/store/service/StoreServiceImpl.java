package org.example.outlivryteamproject.domain.store.service;


import lombok.RequiredArgsConstructor;
import org.example.outlivryteamproject.common.UpdateUtils;
import org.example.outlivryteamproject.domain.store.dto.request.StoreRequestDto;
import org.example.outlivryteamproject.domain.store.dto.request.updateStoreRequestDto;
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
    public StoreResponseDto saveStore(StoreRequestDto requsetDto) {

        Store store = new Store(requsetDto);
        Store savedStore = storeRepository.save(store);

        return new StoreResponseDto(savedStore);
    }

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
        Store store = storeRepository.findByStoreIdWithMenuListOrElseThrow(storeId);

        return new findOneStoreResponseDto(store);
    }

    @Override
    @Transactional
    public StoreResponseDto updateStore(Long storeId, updateStoreRequestDto requsetDto) {
        Store store = storeRepository.findByStoreIdOrElseThrow(storeId);

        // 새로운 값이 있다면 수정, 없다면 기존값 유지
        UpdateUtils.updateString(requsetDto.getNewStoreName(),store::setStoreName);
        UpdateUtils.updateString(requsetDto.getNewStroePictureUrl(),store::setStroePictureUrl);
        UpdateUtils.updateString(requsetDto.getNewPhone(),store::setPhone);
        UpdateUtils.updateString(requsetDto.getNewAddress(),store::setAddress);
        UpdateUtils.updateString(requsetDto.getNewContent(),store::setContent);
        UpdateUtils.updateString(requsetDto.getNewCategory(),store::setCategory);
        UpdateUtils.updateLong(requsetDto.getNewMinDeliveryPrice(), store::setMinDeliveryPrice);
        UpdateUtils.updateLong(requsetDto.getNewDeliveryTip(), store::setDeliveryTip);
        UpdateUtils.updateString(requsetDto.getNewDeliveryAddress(), store::setDeliveryAddress);
        UpdateUtils.updateString(requsetDto.getNewOperationHours(), store::setOperationHours);
        UpdateUtils.updateString(requsetDto.getNewClosedDays(), store::setClosedDays);

        return new StoreResponseDto(store);
    }

    @Override
    public void deleteStore(Long storeId) {
        Store store = storeRepository.findByStoreIdOrElseThrow(storeId);

        storeRepository.delete(store);
    }
}
