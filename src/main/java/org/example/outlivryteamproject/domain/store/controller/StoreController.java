package org.example.outlivryteamproject.domain.store.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.outlivryteamproject.domain.store.dto.request.StoreRequsetDto;
import org.example.outlivryteamproject.domain.store.dto.response.StoreResponseDto;
import org.example.outlivryteamproject.domain.store.service.StoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @PostMapping("/store")
    public ResponseEntity<StoreResponseDto> saveStore (
        @RequestBody @Valid StoreRequsetDto requsetDto
    ){
        return ResponseEntity.ok(storeService.save(requsetDto));
    }
}
