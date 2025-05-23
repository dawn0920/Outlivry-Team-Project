package org.example.outlivryteamproject.domain.store.repository;


import static org.junit.jupiter.api.Assertions.*;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.example.outlivryteamproject.exception.CustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StoreRepositoryTest {

    @Autowired
    private StoreRepository storeRepository;

    @Test
    @DisplayName("")
    void findByStoreNameContainingOrElseThrow() {
        // Given
        Pageable pageable = PageRequest.of(0,5, Sort.by(Direction.DESC, "createTime"));
        String storeName = "존재 하지 않는 가게";

        // When
        CustomException exception = assertThrows(
            CustomException.class,
            () -> storeRepository.findByStoreNameContainingOrElseThrow(storeName,pageable)
        );

        // Then
        assertEquals("해당 가게를 찾을 수 없습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("해당 가게를 찾을 수 없습니다.")
    void findByStoreIdOrElseThrow_notFound() {
        // Given

        // When
        CustomException exception = assertThrows(
            CustomException.class,
            () -> storeRepository.findByStoreIdOrElseThrow(100L)
        );

        // Then
        assertEquals("해당 가게를 찾을 수 없습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("해당 가게와 메뉴리스트를 찾을 수 없습니다.")
    void findByStoreIdWithMenuListOrElseThrow_notFound() {
        // Given

        // When
        CustomException exception = assertThrows(
            CustomException.class,
            () -> storeRepository.findByStoreIdWithMenuListOrElseThrow(100L)
        );

        // Then
        assertEquals("해당 가게와 메뉴리스트를 찾을 수 없습니다.", exception.getMessage());
    }
}