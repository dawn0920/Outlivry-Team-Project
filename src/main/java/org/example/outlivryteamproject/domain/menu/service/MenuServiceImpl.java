package org.example.outlivryteamproject.domain.menu.service;

import lombok.RequiredArgsConstructor;
import org.example.outlivryteamproject.domain.menu.dto.requestDto.MenuRequestDto;
import org.example.outlivryteamproject.domain.menu.dto.responseDto.MenuResponseDto;
import org.example.outlivryteamproject.domain.menu.entity.Menu;
import org.example.outlivryteamproject.domain.menu.repository.MenuRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;

    private final StoreRepository storeRepository;


    @Override
    @Transactional
    public MenuResponseDto createMenu(MenuRequestDto menuRequestDto) {

        Store store = matchesOwner(menuRequestDto.getUserId(), menuRequestDto.getStoreId());

        Menu menu = new Menu(menuRequestDto);
        menu.setStore(store);

        Menu saveMenu = menuRepository.save(menu);

        return new MenuResponseDto(saveMenu);
    }

    @Override
    public MenuResponseDto modifiedMenu(MenuRequestDto menuRequestDto, Long menuId) {

        Store store = matchesOwner(menuRequestDto.getUserId(), menuRequestDto.getStoreId());

        Menu findMenuById = menuRepository.findMenuByIdOrElseThrow(menuId);

        if(store != findMenuById.getStore()){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        if(menuRequestDto.getMenuName() != null){
            findMenuById.setMenuName(menuRequestDto.getMenuName());
        }
        if(menuRequestDto.getPrice() != null){
            findMenuById.setPrice(menuRequestDto.getPrice());
        }
        if(menuRequestDto.getImage() != null){
            findMenuById.setImage(menuRequestDto.getImage());
        }
        if(menuRequestDto.getStatus() != null){
            findMenuById.setStatus(menuRequestDto.getStatus());
        }

        return new MenuResponseDto(findMenuById);
    }

    @Override
    public void deleteMenu(Long userId, Long storeId, Long menuId) {

        Store store = matchesOwner(userId, storeId);

        Menu findMenuById = menuRepository.findMenuByIdOrElseThrow(menuId);

        if(store != findMenuById.getStore()){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        findMenuById.setStatus(0);
    }

    @Override
    public MenuResponseDto findMenuById(Long menuId) {

        Menu findMenuById = menuRepository.findMenuByIdOrElseThrow(menuId);

        return new MenuResponseDto(findMenuById);
    }

    @Override
    public List<MenuResponseDto> findAllMenusByStore(Long storeId) {

        Store store = StoreRepository.findByStoreIdOrElseThrow(storeId);

        List<Menu> menus = menuRepository.findAllByStoreAndStatusNot(store, 0);

        return menus.stream()
                .map(MenuResponseDto::new)
                .collect(Collectors.toList());
    }

    // 주인인지 확인하는 함수
    private Store matchesOwner(Long userId, Long storeId){
        Store store = StoreRepository.findByStoreIdOrElseThrow(storeId);

        Long storeOwnerId = store.getUser().getUserId();

        if(!storeOwnerId.equals(userId)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return store;
    }

}
