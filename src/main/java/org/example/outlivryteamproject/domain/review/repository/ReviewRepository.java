package org.example.outlivryteamproject.domain.review.repository;

import org.example.outlivryteamproject.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
