package org.example.outlivryteamproject.domain.cart.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.example.outlivryteamproject.common.BaseEntity;
import org.example.outlivryteamproject.domain.menu.entity.Menu;
import org.example.outlivryteamproject.domain.user.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Entity
@Table(name = "cart")
@Getter
public class Cart extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @Column(nullable = false)
    private String menuName;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer quantity = 1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_Id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_Id")
    private Menu menu;

    @Column(nullable = false)
    private boolean active;

    public Cart() {

    }

    public Cart(User user, Menu menu) {
        this.menuName = menu.getMenuName();
        this.price = menu.getPrice();
        this.user = user;
        this.menu = menu;
        this.active = true;
    }

    @Transactional
    public void increaseQuantity() {
        this.quantity++;
    }

    public void changeActive() {
        if (this.getCreateTime().isBefore(LocalDateTime.now().minusDays(1))) {
            this.active = false;
        }
    }
}
