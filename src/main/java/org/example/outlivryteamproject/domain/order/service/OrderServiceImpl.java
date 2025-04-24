package org.example.outlivryteamproject.domain.order.service;

import lombok.RequiredArgsConstructor;
import org.example.outlivryteamproject.domain.cart.entity.Cart;
import org.example.outlivryteamproject.domain.cart.repository.CartRepository;
import org.example.outlivryteamproject.domain.order.dto.requestDto.OrderRequestDto;
import org.example.outlivryteamproject.domain.order.dto.responseDto.OrderResponseDto;
import org.example.outlivryteamproject.domain.order.entity.Order;
import org.example.outlivryteamproject.domain.order.repository.OrderRepository;
import org.example.outlivryteamproject.domain.store.repository.StoreRepository;
import org.example.outlivryteamproject.domain.user.entity.User;
import org.example.outlivryteamproject.domain.user.enums.UserRole;
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
    private final StoreRepository storeRepository;

    @Override
    @Transactional
    public OrderResponseDto createOrder(Long userId, OrderRequestDto requestDto) {

        User user = userRepository.findByIdOrElseThrow(userId);
        List<Cart> carts = cartRepository.findCartByUserId(userId);

        if (carts.isEmpty()) {
            throw new RuntimeException("장바구니가 비어있습니다");
        }

        Integer totalPrice = carts.stream()
                .mapToInt(cart -> cart.getPrice() * cart.getQuantity())
                .sum();

        Order order = new Order(user, carts, totalPrice, requestDto);
        Order savedOrder = orderRepository.save(order);

        cartRepository.deleteAllByUserId(userId);

        return new OrderResponseDto(savedOrder);
    }

    @Override
    public OrderResponseDto findByOrderId(Long userId, Long orderId) {

        Order findedOrder = orderRepository.findByOrderIdOrElseThrow(orderId);

        if (!findedOrder.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("접근할 수 없습니다");
        }

        return new OrderResponseDto(findedOrder);
    }

    @Override
    @Transactional
    public void receivedOrder(Long userId, Long orderId) {

        User findedUser = userRepository.findByIdOrElseThrow(userId);
        Order findedOrder = orderRepository.findByOrderIdOrElseThrow(orderId);

        if (findedUser.getUserRole().equals(UserRole.USER)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        if (!findedOrder.getStore().getUser().equals(findedUser)) {
            throw new IllegalArgumentException("사장님만 변경할 수 있습니다.");
        }

        findedOrder.changeReceived();
    }

    @Override
    @Transactional
    public void deliveryOrder(Long userId, Long orderId) {

        User findedUser = userRepository.findByIdOrElseThrow(userId);
        Order findedOrder = orderRepository.findByOrderIdOrElseThrow(orderId);

        if (findedUser.getUserRole().equals(UserRole.USER)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        if (!findedOrder.getStore().getUser().equals(findedUser)) {
            throw new IllegalArgumentException("사장님만 변경할 수 있습니다.");
        }

        findedOrder.changeDelivery();
    }

    @Override
    @Transactional
    public void deleteOrder(Long userId, Long orderId) {

        User findedUser = userRepository.findByIdOrElseThrow(userId);
        Order findedOrder = orderRepository.findByOrderIdOrElseThrow(orderId);

        if (!findedUser.equals(findedOrder.getUser())) {
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
        }

        orderRepository.delete(findedOrder);

    }
}
