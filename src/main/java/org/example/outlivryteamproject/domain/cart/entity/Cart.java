package org.example.outlivryteamproject.domain.cart.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.example.outlivryteamproject.common.BaseEntity;
import org.example.outlivryteamproject.domain.menu.entity.Menu;

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

    //유저 추가하기

    @ManyToOne
    @JoinColumn(name = "menuId")
    private Menu menu;

    public Cart() {

    }

    public Cart(Long userId, Menu menu) {
        this.menuName = menu.getMenuName();
        this.price = menu.getPrice();
        this.menu = menu;
    }
}
