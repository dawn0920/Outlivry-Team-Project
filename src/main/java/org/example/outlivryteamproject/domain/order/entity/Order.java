package org.example.outlivryteamproject.domain.order.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.outlivryteamproject.common.BaseEntity;
import org.example.outlivryteamproject.domain.cart.entity.Cart;
import org.example.outlivryteamproject.domain.order.dto.requestDto.OrderRequestDto;
import org.example.outlivryteamproject.domain.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Cart> carts;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private Integer totalPrice;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private boolean received;

    @Column(nullable = false)
    private boolean delivery;

    private String request;

    public Order() {

    }

    public Order(User user, List<Cart> carts, Integer totalPrice, OrderRequestDto requestDto) {
        this.user = user;
        this.carts = carts;
        this.totalPrice = totalPrice;
        this.address = user.getAddress();
        this.request = (requestDto.getRequest() != null) ? requestDto.getRequest() : "";
    }

    public void changeReceived() {
        this.received = true;
    }

    public void changeDelivery() {
        this.delivery = true;
    }
}
