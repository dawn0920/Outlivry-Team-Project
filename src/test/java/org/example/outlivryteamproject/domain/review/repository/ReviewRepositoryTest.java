package org.example.outlivryteamproject.domain.review.repository;

import org.example.outlivryteamproject.domain.review.entity.Review;
import org.example.outlivryteamproject.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;

//    @Test
//    void 리뷰_아이디로_찾기() {
//
//    }
}