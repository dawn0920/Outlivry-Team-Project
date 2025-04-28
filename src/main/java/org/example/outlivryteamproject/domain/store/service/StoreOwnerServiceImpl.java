package org.example.outlivryteamproject.domain.store.service;


import lombok.RequiredArgsConstructor;
import org.example.outlivryteamproject.common.S3ImageUploader;
import org.example.outlivryteamproject.common.UpdateUtils;
import org.example.outlivryteamproject.domain.menu.entity.Menu;
import org.example.outlivryteamproject.domain.store.dto.request.StoreRequestDto;
import org.example.outlivryteamproject.domain.store.dto.request.UpdateStoreRequestDto;
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

    private final S3ImageUploader s3ImageUploader;

    @Override
    @Transactional
    public StoreResponseDto saveStore(StoreRequestDto requestDto, Long userId) {

        // 같은 가게 명칭으로 생성 막기
        if(storeRepository.existsByStoreName(requestDto.getStoreName())) {
            throw new CustomException(ExceptionCode.STORE_NAME_DUPLICATED);
        }

        // userId로 검색한 store 수가 3개 이상이면 생성 제한-------------------
        long storeCount = storeRepository.countByUserId(userId);

        if(storeCount>=3) {
            throw new CustomException(ExceptionCode.STORE_LIMIT_EXCEEDED);
        }
        //----------------------------------------------------------------

        User user = userRepository.findByIdOrElseThrow(userId);

        String storePictureUrl = s3ImageUploader.uploadImage(requestDto.getStorePicture());

        Store store = new Store(requestDto, user, storePictureUrl);
        Store savedStore = storeRepository.save(store);

        return new StoreResponseDto(savedStore);
    }

    @Override
    @Transactional
    public StoreResponseDto updateStore(Long storeId, UpdateStoreRequestDto requestDto, Long userId) {

        User user = userRepository.findByIdOrElseThrow(userId);
        Store store = storeRepository.findByStoreIdOrElseThrow(storeId);

        if(!store.getUser().equals(user)) {
            throw new CustomException(ExceptionCode.NOT_EQUALS_OWNER);
        }

        // 새로운 값이 있다면 수정, 없다면 기존값 유지
        String storePictureUrl = s3ImageUploader.uploadImage(requestDto.getNewStorePicture());
        UpdateUtils.updateString(requestDto.getNewStoreName(),store::setStoreName);
        UpdateUtils.updateString(storePictureUrl,store::setStorePictureUrl);
        UpdateUtils.updateString(requestDto.getNewPhone(),store::setPhone);
        UpdateUtils.updateString(requestDto.getNewAddress(),store::setAddress);
        UpdateUtils.updateString(requestDto.getNewContent(),store::setContent);
        UpdateUtils.updateString(requestDto.getNewCategory(),store::setCategory);
        UpdateUtils.updateLong(requestDto.getNewMinDeliveryPrice(), store::setMinDeliveryPrice);
        UpdateUtils.updateLong(requestDto.getNewDeliveryTip(), store::setDeliveryTip);
        UpdateUtils.updateLocalTime(requestDto.getNewOpenTime(), store::setOpenTime);
        UpdateUtils.updateLocalTime(requestDto.getNewCloseTime(), store::setCloseTime);

        return new StoreResponseDto(store);
    }

    @Override
    @Transactional
    public void deleteStore(Long storeId, Long userId) {

        User user = userRepository.findByIdOrElseThrow(userId);
        Store store = storeRepository.findByStoreIdOrElseThrow(storeId);

        if(!store.getUser().equals(user)) {
            throw new CustomException(ExceptionCode.NOT_EQUALS_OWNER);
        }

        store.isDelete();
        for(Menu menu : store.getMenuList()) {
            menu.isDelete();
        }
    }
}
