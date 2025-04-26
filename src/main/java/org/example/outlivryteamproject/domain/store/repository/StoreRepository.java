package org.example.outlivryteamproject.domain.store.repository;


import org.example.outlivryteamproject.domain.store.entity.Store;
import org.example.outlivryteamproject.exception.CustomException;
import org.example.outlivryteamproject.exception.ExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    // 해당 userId로 store 갯수를 카운터함.
    long countByUserId(Long userId);

    // 가게 이름으로 조회
    @EntityGraph(attributePaths = "menuList")
    Page<Store> findByStoreNameContaining(String storeName, Pageable pageable);

    // 가게 조회 - 메뉴 리스트 포함 x
    @Query("SELECT s FROM Store s LEFT JOIN FETCH s.menuList WHERE s.storeId = :id")
    default Store findByStoreIdOrElseThrow(@Param("id") Long StoreId) {
        return findById(StoreId).orElseThrow(
            () -> new CustomException(ExceptionCode.STORE_NOT_FOUND));
    }

    // 가게 단건 조회시 메뉴 리스트도 포함
    default Store findByStoreIdWithMenuListOrElseThrow(Long StoreId) {
        return findById(StoreId).orElseThrow(
            () -> new CustomException(ExceptionCode.STORE_NOT_FOUND));
    }

    // 가게 명칭 중복 방지
    boolean existsByStoreName(String storeName);
}
