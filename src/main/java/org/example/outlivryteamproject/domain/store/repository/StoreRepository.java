package org.example.outlivryteamproject.domain.store.repository;

import org.example.outlivryteamproject.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    default Store findByStoreIdOrElseThrow(Long StoreId) {
        return findById(StoreId).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
