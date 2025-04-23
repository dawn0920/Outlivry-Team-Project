package org.example.outlivryteamproject.domain.store.repository;

import org.example.outlivryteamproject.domain.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    Page<Store> findByStoreNameContaining(String storeName, Pageable pageable);

    // 가게 id 찾는 방식인데 지금은 안씀. 가게 조회때도 안쓰면 삭제함.
    default Store findByStoreIdOrElseThrow(Long StoreId) {
        return findById(StoreId).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
