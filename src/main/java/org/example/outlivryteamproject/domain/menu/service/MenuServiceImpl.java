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

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;

    private final StoreRepository storeRepository;


    @Override
    @Transactional
    public MenuResponseDto createMenu(MenuRequestDto menuRequestDto) {

        Stores store = matchesOwner(menuRequestDto.getUserId(), menuRequestDto.getStoreId());

        Menu menu = new Menu(menuRequestDto);
        menu.setStore(store);

        Menu saveMenu = menuRepository.save(menu);

        return new MenuResponseDto(saveMenu);
    }

    // 주인인지 확인하는 함수
    private Stores matchesOwner(Long userId, Long storeId){
        Stores store = StoreRepository.findById(storeId);

        Long storeOwnerId = store.getUser().getUserId();

        if(!storeOwnerId.equals(userId)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return store;
    }

}
