package org.example.outlivryteamproject.domain.order.dto.requestDto;

import jakarta.annotation.Nullable;
import lombok.Getter;

@Getter
public class OrderRequestDto {

    private final String request;

    public OrderRequestDto(String request) {
        this.request = request;
    }
}
