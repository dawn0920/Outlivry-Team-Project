package org.example.outlivryteamproject.domain.order.service;

import lombok.RequiredArgsConstructor;
import org.example.outlivryteamproject.domain.cart.entity.Cart;
import org.example.outlivryteamproject.domain.cart.repository.CartRepository;
import org.example.outlivryteamproject.domain.order.dto.responseDto.OrderResponseDto;
import org.example.outlivryteamproject.domain.order.entity.Order;
import org.example.outlivryteamproject.domain.order.repository.OrderRepository;
import org.example.outlivryteamproject.domain.user.entity.User;
import org.example.outlivryteamproject.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public OrderResponseDto createOrder(Long userId) {

        User user = userRepository.findById(userId);
        List<Cart> carts = cartRepository.findCartByUserId(userId);

        if (carts.isEmpty()) {
            throw new RuntimeException("장바구니가 비어있습니다");
        }

        Integer totalPrice = carts.stream()
                .mapToInt(cart -> cart.getPrice() * cart.getQuantity())
                .sum();

        Order order = new Order(user, carts, totalPrice);
        Order savedOrder = orderRepository.save(order);

        return new OrderResponseDto(savedOrder);
    }

    @Override
    public OrderResponseDto findByOrderId(Long orderId) {

        Order findedOrder = orderRepository.findByOrderIdOrElseThrow(orderId);

        return new OrderResponseDto(findedOrder);
    }
}
