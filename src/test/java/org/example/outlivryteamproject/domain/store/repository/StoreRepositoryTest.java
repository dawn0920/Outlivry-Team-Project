package org.example.outlivryteamproject.domain.store.repository;


import static org.junit.jupiter.api.Assertions.*;

import org.example.outlivryteamproject.exception.CustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StoreRepositoryTest {

    @Autowired
    private StoreRepository storeRepository;

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