package org.example.outlivryteamproject.domain.cart.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.example.outlivryteamproject.common.BaseEntity;

@Entity
public class Cart extends BaseEntity {
    @Id
    private Long id;

}
