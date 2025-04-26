package org.example.outlivryteamproject.domain.review.repository;

import org.example.outlivryteamproject.domain.review.entity.Review;
import org.example.outlivryteamproject.domain.store.entity.Store;
import org.example.outlivryteamproject.exception.CustomException;
import org.example.outlivryteamproject.exception.ExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findByStoreAndStarsBetween(Store store,Integer starsAfter, Integer starsBefore, Pageable pageable);

    default Review findByReviewIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() ->
                new CustomException(ExceptionCode.REVIEW_NOT_FOUND)
        );
    }

    Page<Review> findByStore(Store store, Pageable pageable);
}
