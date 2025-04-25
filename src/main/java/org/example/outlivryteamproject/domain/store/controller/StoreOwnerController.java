package org.example.outlivryteamproject.domain.store.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.outlivryteamproject.domain.store.dto.request.StoreRequestDto;
import org.example.outlivryteamproject.domain.store.dto.request.updateStoreRequestDto;
import org.example.outlivryteamproject.domain.store.dto.response.StoreResponseDto;
import org.example.outlivryteamproject.domain.store.service.StoreOwnerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.outlivryteamproject.common.TokenUserId;

@RestController
@RequestMapping("/owners/stores")
@RequiredArgsConstructor
public class StoreOwnerController {

    private final StoreOwnerService storeOwnerService;
    private final TokenUserId tokenUserId;

    /**  사장님만 생성 할 수 있도록 수정 필요
     * 가게 생성 메소드
     *
     * @param requestDto 가게 생성 요청 정보를 담은 StoreRequestDto 객체
     * @return 생성된 가게 정보가 담겨 있는 StoreResponseDto 객체
     */
    @PostMapping
    public ResponseEntity<StoreResponseDto> saveStore (
        @ModelAttribute @Valid StoreRequestDto requestDto,
        @RequestHeader("Authorization") String authHeader
    ){
        Long userId = tokenUserId.getTokenUserId(authHeader);

        return new ResponseEntity<>(storeOwnerService.saveStore(requestDto,userId), HttpStatus.CREATED);
    }

    /**  추후 사장님 본인만 가능하도록 수정 필요 + 비밀번호 요구
     * 가게 수정 메소드
     *
     * @param storeId 가게 id를 조회
     * @param requestDto 새로운 가게 정보 담은 updateStoreRequestDto 객체 - null 값 허용
     * @return 바뀐 가게 정보가 담겨 있는 StoreResponseDto 객체 - null 값으로 들어온 정보는 수정 x
     */
    @PatchMapping("/{storeId}")
    public ResponseEntity<StoreResponseDto> updateStore (
        @PathVariable("storeId") Long storeId,
        @ModelAttribute @Valid updateStoreRequestDto requestDto,
        @RequestHeader("Authorization") String authHeader
    ) {
        Long userId = tokenUserId.getTokenUserId(authHeader);

        return new ResponseEntity<>(storeOwnerService.updateStore(storeId, requestDto,userId),HttpStatus.OK);
    }

    /** 사장님 본인만 삭제 가능하도록 수정 필요 + 비밀번호 요구
     * 가게 삭제 메소드 - soft delete 사용
     *
     * @param storeId 가게 id를 조회
     * @return 없음 - 상태만 표시
     */
    @DeleteMapping("/{storeId}")
    public ResponseEntity<Void> deleteStore (
        @PathVariable("storeId") Long storeId,
        @RequestHeader("Authorization") String authHeader
    ) {
        Long userId = tokenUserId.getTokenUserId(authHeader);

        storeOwnerService.deleteStore(storeId, userId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
