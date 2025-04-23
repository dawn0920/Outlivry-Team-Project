package org.example.outlivryteamproject.domain.order.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import org.example.outlivryteamproject.common.BaseEntity;

@Entity
@Table(name = "orders")
@Getter
public class Order extends BaseEntity {

    @Id
    private Long id;

}
