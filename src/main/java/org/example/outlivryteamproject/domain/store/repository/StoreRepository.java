package org.example.outlivryteamproject.domain.store.repository;

import java.util.Optional;
import org.example.outlivryteamproject.domain.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    // 가게 이름으로 조회
    @EntityGraph(attributePaths = "menuList")
    Page<Store> findByStoreNameContaining(String storeName, Pageable pageable);

    // 가게 조회
    default Store findByStoreIdOrElseThrow(Long StoreId) {
        return findById(StoreId).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Query("SELECT s FROM Store s LEFT JOIN FETCH s.menuList WHERE s.storeId = :id")
    Optional<Store> findByStoreIdWithMenuList(@Param("id") Long StoreId);

    default Store findByStoreIdWithMenuListOrElseThrow(Long StoreId) {
        return findByStoreIdWithMenuList(StoreId).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
