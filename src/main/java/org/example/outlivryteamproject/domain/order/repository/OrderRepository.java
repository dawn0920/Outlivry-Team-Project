package org.example.outlivryteamproject.domain.order.repository;

import org.example.outlivryteamproject.domain.order.entity.Order;
import org.example.outlivryteamproject.domain.user.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(attributePaths = "orderItems")
    default Order findByOrderIdOrElseThrow(Long orderId) {
        return findById(orderId).orElseThrow(() ->
                new RuntimeException("해당 주문이 존재하지 않습니다"));
    }

    Optional<Order> findByUser(User user);

    default Order findByUserOrElseThrow(User user) {
        return findByUser(user).orElseThrow(() ->
                new NoSuchElementException("해당 주문이 존재하지 않습니다."));
    }

}
