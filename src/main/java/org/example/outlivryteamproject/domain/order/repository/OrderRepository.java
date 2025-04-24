package org.example.outlivryteamproject.domain.order.repository;

import org.example.outlivryteamproject.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    default Order findByOrderIdOrElseThrow(Long orderId) {
        return findById(orderId).orElseThrow(() ->
                new RuntimeException("해당 주문이 존재하지 않습니다"));
    }
}
