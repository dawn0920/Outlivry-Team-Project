package org.example.outlivryteamproject.domain.review.service;

import lombok.RequiredArgsConstructor;
import org.example.outlivryteamproject.domain.review.dto.requestDto.CreateReviewRequestDto;
import org.example.outlivryteamproject.domain.review.dto.requestDto.FindByStarsRequestDto;
import org.example.outlivryteamproject.domain.review.dto.requestDto.UpdateReviewRequestDto;
import org.example.outlivryteamproject.domain.review.dto.responseDto.CreateReviewResponseDto;
import org.example.outlivryteamproject.domain.review.dto.responseDto.FindReviewResponseDto;
import org.example.outlivryteamproject.domain.review.dto.responseDto.UpdateReviewResponseDto;
import org.example.outlivryteamproject.domain.review.entity.Review;
import org.example.outlivryteamproject.domain.review.repository.ReviewRepository;
import org.example.outlivryteamproject.domain.store.entity.Store;
import org.example.outlivryteamproject.domain.store.repository.StoreRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;

    @Override
    @Transactional
    public CreateReviewResponseDto save(Long storeId, CreateReviewRequestDto requestDto) {

        Store findedStore = storeRepository.findByStoreIdOrElseThrow(storeId);

        Review review = new Review(findedStore, requestDto);
        Review savedReview = reviewRepository.save(review);

        return new CreateReviewResponseDto(savedReview);
    }

    @Override
    public Page<FindReviewResponseDto> findAll(Long storeId, int page) {

        Store findedStore = storeRepository.findByStoreIdOrElseThrow(storeId);

        int adjustedPage = (page > 0) ? page - 1 : 0;
        PageRequest pageable = PageRequest.of(adjustedPage, 10, Sort.by("creatTime").descending());
        Page<Review> reviews = reviewRepository.findByStore(findedStore, pageable);

        return reviews.map(FindReviewResponseDto::new);
    }

    @Override
    public Page<FindReviewResponseDto> findByStars(Long storeId, int page, FindByStarsRequestDto requestDto) {

        Store findedStore = storeRepository.findByStoreIdOrElseThrow(storeId);

        int adjustPage = (page > 0) ? page - 1 : 0;
        PageRequest pageable = PageRequest.of(adjustPage, 10, Sort.by("creatTime").descending());

        Page<Review> reviews = reviewRepository.findByStoreAndStarsBetween(findedStore, requestDto.getStart(), requestDto.getEnd(), pageable);

        return reviews.map(FindReviewResponseDto::new);

    }

    @Override
    @Transactional
    public UpdateReviewResponseDto update(Long reviewId, UpdateReviewRequestDto requestDto) {

        Review findedReview = reviewRepository.findByReviewIdOrElseThrow(reviewId);
        findedReview.update(requestDto);

        Review savedReview = reviewRepository.save(findedReview);

        return new UpdateReviewResponseDto(savedReview);
    }

    @Override
    @Transactional
    public void delete(Long reviewId) {
        Review review = reviewRepository.findByReviewIdOrElseThrow(reviewId);
        reviewRepository.delete(review);
    }
}
