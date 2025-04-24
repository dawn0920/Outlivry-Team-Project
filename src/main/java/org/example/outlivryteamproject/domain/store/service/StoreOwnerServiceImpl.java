package org.example.outlivryteamproject.domain.store.service;


import lombok.RequiredArgsConstructor;
import org.example.outlivryteamproject.common.UpdateUtils;
import org.example.outlivryteamproject.domain.store.dto.request.StoreRequestDto;
import org.example.outlivryteamproject.domain.store.dto.request.updateStoreRequestDto;
import org.example.outlivryteamproject.domain.store.dto.response.StoreResponseDto;
import org.example.outlivryteamproject.domain.store.entity.Store;
import org.example.outlivryteamproject.domain.store.repository.StoreRepository;
import org.example.outlivryteamproject.domain.user.entity.User;
import org.example.outlivryteamproject.domain.user.repository.UserRepository;
import org.example.outlivryteamproject.exception.CustomException;
import org.example.outlivryteamproject.exception.ExceptionCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StoreOwnerServiceImpl implements StoreOwnerService{

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public StoreResponseDto saveStore(StoreRequestDto requsetDto, Long userId) {

        // userId로 검색한 store 수가 3개 이상이면 생성 제한-------------------
        long storeCount = storeRepository.countByUserId(userId);

        if(storeCount>3) {
            throw new CustomException(ExceptionCode.STORE_LIMIT_EXCEEDED);
        }
        //----------------------------------------------------------------

        User user = userRepository.findByIdOrElseThrow(userId);

        Store store = new Store(requsetDto, user);
        Store savedStore = storeRepository.save(store);

        return new StoreResponseDto(savedStore);
    }

    @Override
    @Transactional
    public StoreResponseDto updateStore(Long storeId, updateStoreRequestDto requsetDto, Long userId) {

        User user = userRepository.findByIdOrElseThrow(userId);
        Store store = storeRepository.findByStoreIdOrElseThrow(storeId);

        if(!store.getUser().equals(user)) {
            throw new CustomException(ExceptionCode.NOT_EQUALS_OWNER);
        }

        // 새로운 값이 있다면 수정, 없다면 기존값 유지
        UpdateUtils.updateString(requsetDto.getNewStoreName(),store::setStoreName);
        UpdateUtils.updateString(requsetDto.getNewStorePictureUrl(),store::setStorePictureUrl);
        UpdateUtils.updateString(requsetDto.getNewPhone(),store::setPhone);
        UpdateUtils.updateString(requsetDto.getNewAddress(),store::setAddress);
        UpdateUtils.updateString(requsetDto.getNewContent(),store::setContent);
        UpdateUtils.updateString(requsetDto.getNewCategory(),store::setCategory);
        UpdateUtils.updateLong(requsetDto.getNewMinDeliveryPrice(), store::setMinDeliveryPrice);
        UpdateUtils.updateLong(requsetDto.getNewDeliveryTip(), store::setDeliveryTip);
        UpdateUtils.updateString(requsetDto.getNewOperationHours(), store::setOperationHours);

        return new StoreResponseDto(store);
    }

    @Override
    public void deleteStore(Long storeId, Long userId) {

        User user = userRepository.findByIdOrElseThrow(userId);
        Store store = storeRepository.findByStoreIdOrElseThrow(storeId);

        if(!store.getUser().equals(user)) {
            throw new CustomException(ExceptionCode.NOT_EQUALS_OWNER);
        }

        storeRepository.delete(store);
    }
}
