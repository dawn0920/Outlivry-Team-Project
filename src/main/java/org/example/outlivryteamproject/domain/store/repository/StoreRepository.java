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

    @EntityGraph(attributePaths = "menuList")
    Page<Store> findByStoreNameContaining(String storeName, Pageable pageable);

    @Query("SELECT s FROM Store s LEFT JOIN FETCH s.menuList WHERE s.storeId = :id")
    Optional<Store> findByStoreIdwithMenu(@Param("id") Long StoreId);

    default Store findByStoreIdOrElseThrow(Long StoreId) {
        return findByStoreIdwithMenu(StoreId).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
