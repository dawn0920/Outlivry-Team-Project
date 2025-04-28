package org.example.outlivryteamproject.domain.menu.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.outlivryteamproject.common.BaseEntity;
import org.example.outlivryteamproject.domain.menu.dto.requestDto.MenuRequestDto;
import org.example.outlivryteamproject.domain.store.entity.Store;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "Menu")
@Getter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE menu SET is_deleted = true WHERE menu_id = ?")
@Where(clause = "is_deleted = false")
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;

    @Setter
    private String menuName;

    @Setter
    private Integer price;

    @Setter
    private String imageUrl;

    @Setter
    private boolean soldOut;

    @Setter
    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    public Menu(MenuRequestDto menuRequestDto, String imageUrl) {
        this.menuName = menuRequestDto.getMenuName();
        this.price = menuRequestDto.getPrice();
        this.imageUrl = imageUrl;
    }
}
