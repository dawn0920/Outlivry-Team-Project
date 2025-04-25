package org.example.outlivryteamproject.domain.order.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.outlivryteamproject.domain.cart.entity.Cart;

@Entity
@Table (name = "orderItem")
@Getter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id")
    private Order order;

    @Column(nullable = false)
    private String menuName;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer quantity;

    public OrderItem() {

    }

    public OrderItem(Cart cart, Order order) {
        this.menuName = cart.getMenuName();
        this.price = cart.getPrice();
        this.quantity = cart.getQuantity();
        this.order = order;
    }
}
