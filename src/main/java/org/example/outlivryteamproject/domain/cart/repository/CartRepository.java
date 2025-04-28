package org.example.outlivryteamproject.domain.cart.repository;

import org.example.outlivryteamproject.domain.cart.entity.Cart;
import org.example.outlivryteamproject.exception.CustomException;
import org.example.outlivryteamproject.exception.ExceptionCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("SELECT c FROM Cart c JOIN FETCH c.user WHERE c.user.id = :userId")
    List<Cart> findCartByUserId(Long userId);

    default Cart findCartByCartIdOrElseThrow(Long cartId) {
        return findById(cartId).orElseThrow(() ->
                new CustomException(ExceptionCode.CART_NOT_FOUND)
        );
    }

    void deleteAllByUserId(Long userId);
}
