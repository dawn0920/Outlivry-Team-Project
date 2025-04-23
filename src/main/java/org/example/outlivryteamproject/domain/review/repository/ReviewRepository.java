package org.example.outlivryteamproject.domain.review.repository;

import org.example.outlivryteamproject.domain.review.dto.responseDto.FindReviewResponseDto;
import org.example.outlivryteamproject.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.server.ResponseStatusException;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findByStarsBetween(Integer starsAfter, Integer starsBefore, Pageable pageable);

    default Review findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() ->
                new RuntimeException("존재하지 않는 리뷰입니다.")
        );
    }
}
