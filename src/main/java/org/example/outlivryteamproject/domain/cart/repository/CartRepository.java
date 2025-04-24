package org.example.outlivryteamproject.domain.cart.repository;

import org.example.outlivryteamproject.domain.cart.entity.Cart;
import org.example.outlivryteamproject.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findCartByUserId(Long userId);

    Long user(User user);

    default Cart findCartByCartIdOrElseThrow(Long cartId) {
        return findById(cartId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 상품입니다.")
        );
    }

    void deleteAllByUserId(Long userId);
}
