package org.example.outlivryteamproject.domain.order.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.example.outlivryteamproject.domain.cart.entity.Cart;
import org.example.outlivryteamproject.domain.menu.entity.Menu;

@Entity
@Table (name = "orderItem")
@Getter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @Column(nullable = false)
    private String menuName;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer quantity;

    public OrderItem() {

    }

    public OrderItem(Cart cart) {
        this.menuName = cart.getMenuName();
        this.price = cart.getPrice();
        this.quantity = cart.getQuantity();
    }
}
