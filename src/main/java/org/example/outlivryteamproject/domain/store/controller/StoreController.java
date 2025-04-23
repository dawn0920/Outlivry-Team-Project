package org.example.outlivryteamproject.domain.store.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.outlivryteamproject.domain.store.dto.request.StoreRequsetDto;
import org.example.outlivryteamproject.domain.store.dto.response.StoreResponseDto;
import org.example.outlivryteamproject.domain.store.dto.response.findOneStoreResponseDto;
import org.example.outlivryteamproject.domain.store.service.StoreService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    /**
     * 가게 생성 메소드
     *
     * @param requsetDto 가게 생성 요청 정보를 담은 StoreRequsetDto 객체
     * @return 생성된 가게 정보가 담겨 있는 StoreResponseDto 객체
     */
    @PostMapping
    public ResponseEntity<StoreResponseDto> saveStore (
        @RequestBody @Valid StoreRequsetDto requsetDto
    ){
        return new ResponseEntity<>(storeService.saveStore(requsetDto), HttpStatus.OK);
    }

    /**
     * 가게 검색 메소드
     *
     * @param page  현재 페이지
     * @param size  페이지당 게시글 개수
     * @param storeName 가게 이름으로 검색 (없으면 전체 조회)
     * @return  여러 가게를 페이지와 사이즈에 따라 표시
     */
    @GetMapping
    public ResponseEntity<Page<StoreResponseDto>> findStoreList (
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        @RequestParam(required = false) String storeName
    ) {
        return new ResponseEntity<>(storeService.getStoreList(page,size,storeName),HttpStatus.OK);
    }

    /**
     * 가게 단건 조회 메소드
     *
     * @param storeId 가게 id를 조회
     * @return  가게의 정보와 메뉴리스트를 표시
     */
    @GetMapping("/{storeId}")
    public ResponseEntity<findOneStoreResponseDto> findOneStore(@PathVariable Long storeId) {
        return new ResponseEntity<>(storeService.getOneStore(storeId),HttpStatus.OK);
    }
}
