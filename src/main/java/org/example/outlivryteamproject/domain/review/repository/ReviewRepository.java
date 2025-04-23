package org.example.outlivryteamproject.domain.review.repository;

import org.example.outlivryteamproject.domain.review.dto.responseDto.FindReviewResponseDto;
import org.example.outlivryteamproject.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findByStarsBetween(Integer starsAfter, Integer starsBefore, Pageable pageable);

}
