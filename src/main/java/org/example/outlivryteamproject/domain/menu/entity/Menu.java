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
@SQLDelete(sql = "UPDATE menu SET status = 0 WHERE id = ?")
@Where(clause = "status <> 0")
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
    private Integer status;

    @Setter
    @ManyToOne
    @JoinColumn(name = "storeId")
    private Store store;

    public Menu(MenuRequestDto menuRequestDto, String imageUrl) {
        this.menuName = menuRequestDto.getMenuName();
        this.price = menuRequestDto.getPrice();
        this.imageUrl = imageUrl;
        if (menuRequestDto.getStatus() == null){
            this.status = 1;
        }else{
            this.status = menuRequestDto.getStatus();
        }
    }
}
