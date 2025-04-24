package org.example.outlivryteamproject.domain.cart.service;

import lombok.RequiredArgsConstructor;
import org.example.outlivryteamproject.domain.cart.dto.responseDto.FindCartResponseDto;
import org.example.outlivryteamproject.domain.cart.dto.responseDto.SaveCartResponseDto;
import org.example.outlivryteamproject.domain.cart.entity.Cart;
import org.example.outlivryteamproject.domain.cart.repository.CartRepository;
import org.example.outlivryteamproject.domain.menu.entity.Menu;
import org.example.outlivryteamproject.domain.menu.repository.MenuRepository;
import org.example.outlivryteamproject.domain.store.entity.Store;
import org.example.outlivryteamproject.domain.user.entity.User;
import org.example.outlivryteamproject.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{

    private final CartRepository cartRepository;
    private final MenuRepository menuRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public SaveCartResponseDto save(Long userId, Long menuId) {

        Menu findedMenu = menuRepository.findMenuByIdOrElseThrow(menuId);
        List<Cart> carts = cartRepository.findCartByUserId(userId);

        //유저 아이디의 장바구니를 조회 후 입력받은 메뉴와 가게가 같은지 검증, 다르다면 장바구니 초기화
        if (!carts.isEmpty()) {
            Store cartStore = carts.get(0).getMenu().getStore();
            Store menuStore = findedMenu.getStore();

            if (!cartStore.equals(menuStore)) {
                cartRepository.deleteAll(carts);
            }
        }

        //나중에 pull 받으면 수정해줘야함
        Optional<User> findedUser = userRepository.findById(userId);
        Cart cart = new Cart(findedUser, findedMenu);
        Cart savedCart = cartRepository.save(cart);

        return new SaveCartResponseDto(savedCart);
    }

    @Override
    public List<FindCartResponseDto> findCart(Long userId) {

        List<Cart> carts = cartRepository.findCartByUserId(userId);

        return carts.stream().
                map(FindCartResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void removeCartItem(Long cartId) {
        Cart cart = cartRepository.findCartByCartIdOrElseThrow(cartId);
        cartRepository.delete(cart);
    }

    @Override
    @Transactional
    public void removeCart(Long userId) {

        List<Cart> carts = cartRepository.findCartByUserId(userId);
        cartRepository.deleteAll(carts);
    }
}
