package org.example.outlivryteamproject.domain.order.service;

import lombok.RequiredArgsConstructor;
import org.example.outlivryteamproject.config.aop.annotaion.OrderStatusLogger;
import org.example.outlivryteamproject.domain.cart.entity.Cart;
import org.example.outlivryteamproject.domain.cart.repository.CartRepository;
import org.example.outlivryteamproject.domain.order.dto.requestDto.OrderRequestDto;
import org.example.outlivryteamproject.domain.order.dto.responseDto.OrderResponseDto;
import org.example.outlivryteamproject.domain.order.entity.Order;
import org.example.outlivryteamproject.domain.order.repository.OrderRepository;
import org.example.outlivryteamproject.domain.store.entity.Store;
import org.example.outlivryteamproject.domain.store.repository.StoreRepository;
import org.example.outlivryteamproject.domain.user.entity.User;
import org.example.outlivryteamproject.domain.user.repository.UserRepository;
import org.example.outlivryteamproject.exception.CustomException;
import org.example.outlivryteamproject.exception.ExceptionCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
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
    @OrderStatusLogger
    public OrderResponseDto createOrder(Long storeId, Long userId, OrderRequestDto requestDto) {

        Store store = storeRepository.findByStoreIdOrElseThrow(storeId);
        LocalTime now = LocalTime.now();

        //영업중인지 확인하기
        if (now.isBefore(store.getOpenTime()) || now.isAfter(store.getCloseTime())) {
            throw new CustomException(ExceptionCode.STORE_CLOSED);
        }

        User user = userRepository.findByIdOrElseThrow(userId);

        //주소가 없으면 예외처리
        if (user.getAddress() == null) {
            throw new CustomException(ExceptionCode.ADDRESS_NOT_FOUND);
        }

        List<Cart> carts = cartRepository.findCartByUserId(userId);

        //장바구니가 비었을 시 예외처리
        if (carts.isEmpty()) {
            throw new CustomException(ExceptionCode.CART_EMPTY);
        }

        //총금액 계산
        Integer totalPrice = carts.stream()
                .mapToInt(cart -> cart.getPrice() * cart.getQuantity())
                .sum();

        //최소 주문 금액보다 낮을 시 예외처리
        if (store.getMinDeliveryPrice() > totalPrice) {
            throw new CustomException(ExceptionCode.DELIVERY_PRICE_LOW);
        }

        Order order = new Order(user, carts, totalPrice, requestDto);

        Order savedOrder = orderRepository.save(order);

        cartRepository.deleteAllByUserId(userId);

        return new OrderResponseDto(savedOrder);
    }

    @Override
    @Transactional
    public OrderResponseDto findByOrderId(Long userId, Long orderId) {

        Order findedOrder = orderRepository.findByOrderIdOrElseThrow(orderId);
        Long orderUserId = findedOrder.getUser().getId();
        Long ownerId = findedOrder.getStore().getUser().getId();


        //로그인한 유저가 주문한 유저나 해당 가게의 사장이 아닐경우 예외처리
        if (!userId.equals(orderUserId) && !userId.equals(ownerId)) {
            throw new CustomException(ExceptionCode.ORDER_ACCESS_DENIED);
        }

        return new OrderResponseDto(findedOrder);
    }

    @Override
    @Transactional
    @OrderStatusLogger
    public OrderResponseDto receivedOrder(Long userId, Long orderId) {

        User findedUser = userRepository.findByIdOrElseThrow(userId);
        Order findedOrder = orderRepository.findByOrderIdOrElseThrow(orderId);

        //해당 가게의 사장님이 아닐경우 예외처리
        if (!findedOrder.getStore().getUser().equals(findedUser)) {
            throw new CustomException(ExceptionCode.ORDER_ACCESS_DENIED);
        }

        findedOrder.changeReceived();

        return new OrderResponseDto(findedOrder);
    }

    @Override
    @Transactional
    @OrderStatusLogger
    public OrderResponseDto deliveryOrder(Long userId, Long orderId) {

        User findedUser = userRepository.findByIdOrElseThrow(userId);
        Order findedOrder = orderRepository.findByOrderIdOrElseThrow(orderId);

        //해당 가게의 사장님이 아닐경우 예외처리
        if (!findedOrder.getStore().getUser().equals(findedUser)) {
            throw new CustomException(ExceptionCode.ORDER_ACCESS_DENIED);
        }

        findedOrder.changeDelivery();

        return new OrderResponseDto(findedOrder);
    }

    @Override
    @Transactional
    public void deleteOrder(Long userId, Long orderId) {

        User findedUser = userRepository.findByIdOrElseThrow(userId);
        Order findedOrder = orderRepository.findByOrderIdOrElseThrow(orderId);

        //주문한 유저가 아닐 경우 예외처리
        if (!findedUser.equals(findedOrder.getUser())) {
            throw new CustomException(ExceptionCode.ORDER_ACCESS_DENIED);
        }

        orderRepository.delete(findedOrder);

    }
}
