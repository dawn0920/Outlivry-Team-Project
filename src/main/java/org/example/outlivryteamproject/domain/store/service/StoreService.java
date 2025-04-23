package org.example.outlivryteamproject.domain.store.service;

import org.example.outlivryteamproject.domain.store.dto.request.StoreRequsetDto;
import org.example.outlivryteamproject.domain.store.dto.response.StoreResponseDto;

public interface StoreService {
    StoreResponseDto save(StoreRequsetDto requsetDto);
}
