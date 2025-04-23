package org.example.outlivryteamproject.domain.menu.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.outlivryteamproject.common.BaseEntity;
import org.example.outlivryteamproject.domain.menu.dto.requestDto.MenuRequestDto;

@Entity
@Table(name = "Menu")
@Getter
@NoArgsConstructor
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;

    @Setter
    private String menuName;

    @Setter
    private Integer price;

    @Setter
    private String image;

    @Setter
    private Integer status;

    @Setter
    @ManyToOne
    @JoinColumn(name = "storeId")
    private Store store;

    public Menu(MenuRequestDto menuRequestDto) {
        this.menuName = menuRequestDto.getMenuName();
        this.price = menuRequestDto.getPrice();
        this.image = menuRequestDto.getImage();
        if (menuRequestDto.getStatus() == null){
            this.status = 1;
        }else{
            this.status = menuRequestDto.getStatus();
        }
    }
}
