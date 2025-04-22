package org.example.outlivryteamproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.outlivryteamproject.common.BaseEntity;

@Entity
@Table(name = "Menu")
@Getter
@NoArgsConstructor
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;

    private Long storeId;

    private String menuName;

    private Integer price;

    private String image;

    private Integer status;

    @Setter
    @ManyToOne
    @JoinColumn(name = "storeId")
    private Store store;

}
