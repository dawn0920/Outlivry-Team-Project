package org.example.outlivryteamproject.domain.order.repository;

import org.example.outlivryteamproject.domain.order.entity.Order;
import org.example.outlivryteamproject.exception.CustomException;
import org.example.outlivryteamproject.exception.ExceptionCode;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(attributePaths = "orderItems")
    default Order findByOrderIdOrElseThrow(Long orderId) {
        return findById(orderId).orElseThrow(() ->
                new CustomException(ExceptionCode.ORDER_NOT_FOUND));
    }

}
