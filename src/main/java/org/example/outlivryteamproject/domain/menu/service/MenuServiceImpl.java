package org.example.outlivryteamproject.domain.menu.service;

import lombok.RequiredArgsConstructor;
import org.example.outlivryteamproject.common.S3ImageUploader;
import org.example.outlivryteamproject.domain.menu.dto.requestDto.MenuRequestDto;
import org.example.outlivryteamproject.domain.menu.dto.requestDto.ModifiedMenuRequestDto;
import org.example.outlivryteamproject.domain.menu.dto.responseDto.MenuResponseDto;
import org.example.outlivryteamproject.domain.menu.entity.Menu;
import org.example.outlivryteamproject.domain.menu.repository.MenuRepository;
import org.example.outlivryteamproject.domain.store.entity.Store;
import org.example.outlivryteamproject.domain.store.repository.StoreRepository;
import org.example.outlivryteamproject.exception.CustomException;
import org.example.outlivryteamproject.exception.ExceptionCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;

    private final StoreRepository storeRepository;

    private final S3ImageUploader s3ImageUploader;


    @Override
    @Transactional
    public MenuResponseDto createMenu(Long storeId, Long userId, MenuRequestDto menuRequestDto) {

        Store store = matchesOwner(userId, storeId);

        String imageUrl = s3ImageUploader.uploadImage(menuRequestDto.getImage());

        Menu menu = new Menu(menuRequestDto, imageUrl);
        menu.setStore(store);

        Menu saveMenu = menuRepository.save(menu);

        return new MenuResponseDto(saveMenu);
    }

    @Transactional
    @Override
    @Transactional
    public MenuResponseDto modifiedMenu(Long storeId, Long userId, ModifiedMenuRequestDto modifiedMenuRequestDto, Long menuId) {

        Store store = matchesOwner(userId, storeId);

        Menu findMenuById = menuRepository.findMenuByIdOrElseThrow(menuId);

        if(store != findMenuById.getStore()){
            throw new CustomException(ExceptionCode.MENU_STORE_MISMATCH);
        }

        if(modifiedMenuRequestDto.getMenuName() != null){
            findMenuById.setMenuName(modifiedMenuRequestDto.getMenuName());
        }
        if(modifiedMenuRequestDto.getPrice() != null){
            findMenuById.setPrice(modifiedMenuRequestDto.getPrice());
        }
        if(modifiedMenuRequestDto.getImage() != null){
            String imageUrl = s3ImageUploader.uploadImage(modifiedMenuRequestDto.getImage());
            findMenuById.setImageUrl(imageUrl);
        }

        return new MenuResponseDto(findMenuById);
    }

    @Transactional
    @Override
    @Transactional
    public void deleteMenu(Long userId, Long storeId, Long menuId) {

        Store store = matchesOwner(userId, storeId);

        Menu findMenuById = menuRepository.findMenuByIdOrElseThrow(menuId);

        if(store != findMenuById.getStore()){
            throw new CustomException(ExceptionCode.MENU_STORE_MISMATCH);
        }

        menuRepository.delete(findMenuById);
    }

//    @Override
//    public MenuResponseDto findMenuById(Long menuId) {
//
//        Menu findMenuById = menuRepository.findMenuByIdOrElseThrow(menuId);
//
//        return new MenuResponseDto(findMenuById);
//    }

    @Transactional
    @Override
    public List<MenuResponseDto> findAllMenusByStore(Long storeId) {

        Store store = storeRepository.findByStoreIdOrElseThrow(storeId);

        List<Menu> menus = menuRepository.findAllByStore(store);

        return menus.stream()
                .map(MenuResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void changeMenuStatus(Long storeId, Long userId, Long menuId) {
        Store store = matchesOwner(userId, storeId);

        Menu findMenuById = menuRepository.findMenuByIdOrElseThrow(menuId);

        if(store != findMenuById.getStore()){
            throw new CustomException(ExceptionCode.MENU_STORE_MISMATCH);
        }

        findMenuById.setSoldOut(!findMenuById.isDeleted());

    }

    // 주인인지 확인하는 함수
    private Store matchesOwner(Long userId, Long storeId){
        Store store = storeRepository.findByStoreIdOrElseThrow(storeId);

        Long storeOwnerId = store.getUser().getId();

        if(!storeOwnerId.equals(userId)){
            throw new CustomException(ExceptionCode.NOT_EQUALS_OWNER);
        }

        return store;
    }
}
