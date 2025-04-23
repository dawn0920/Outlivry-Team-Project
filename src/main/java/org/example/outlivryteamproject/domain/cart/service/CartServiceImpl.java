package org.example.outlivryteamproject.domain.cart.service;

import lombok.RequiredArgsConstructor;
import org.example.outlivryteamproject.domain.cart.dto.responseDto.FindCartByUserIdResponseDto;
import org.example.outlivryteamproject.domain.cart.dto.responseDto.SaveCartResponseDto;
import org.example.outlivryteamproject.domain.cart.entity.Cart;
import org.example.outlivryteamproject.domain.cart.repository.CartRepository;
import org.example.outlivryteamproject.domain.menu.entity.Menu;
import org.example.outlivryteamproject.domain.menu.repository.MenuRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{

    private final CartRepository cartRepository;
    private final MenuRepository menuRepository;

    @Override
    @Transactional
    public SaveCartResponseDto save(Long userId, Long menuId) {

        Menu findedMenu = menuRepository.findMenuByIdOrElseThrow(menuId);
        Long storeId = findedMenu.getStore().getStoreId();

        //유저 아이디의 장바구니를 조회 후 입력받은 메뉴와 같은지 검증하는 로직 추가 예정

        //해당 유저가 동일한 menu를 가지고 있는지 확인 후 가지고 있다면 생성하지않고 수량 증가 로직 추가 예정

        Cart cart = new Cart(userId, findedMenu);
        Cart savedCart = cartRepository.save(cart);

        return new SaveCartResponseDto(savedCart);
    }

    @Override
    public Page<FindCartByUserIdResponseDto> findCart(Long userId, int page) {

        int adjustPage = (page > 0) ? page - 1 : 0;
        PageRequest pageable = PageRequest.of(adjustPage, 10, Sort.by("creatTime").descending());

        //유저 클래스 받은 다음에 해야될거같은데 ?
        cartRepository.findByUserIdOrElseThrow(userId)
    }
}
