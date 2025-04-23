package org.example.outlivryteamproject.domain.order.repository;

import org.example.outlivryteamproject.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
