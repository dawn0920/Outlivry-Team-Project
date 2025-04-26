package org.example.outlivryteamproject.domain.review.repository;

import org.example.outlivryteamproject.domain.review.entity.Review;
import org.example.outlivryteamproject.domain.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findByStoreAndStarsBetween(Store store,Integer starsAfter, Integer starsBefore, Pageable pageable);

    default Review findByReviewIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() ->
                new RuntimeException("존재하지 않는 리뷰입니다.")
        );
    }

    Page<Review> findByStore(Store store, Pageable pageable);

    @Query("SELECT AVG(r.stars) FROM Review r WHERE r.store.storeId = :storeId")
    Double findAverageRatingByStoreId(@Param("storeId") Long storeId);



}
