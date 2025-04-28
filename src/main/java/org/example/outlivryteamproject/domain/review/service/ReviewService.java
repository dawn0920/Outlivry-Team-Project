package org.example.outlivryteamproject.domain.review.service;

import org.example.outlivryteamproject.domain.review.dto.requestDto.CreateReviewRequestDto;
import org.example.outlivryteamproject.domain.review.dto.requestDto.UpdateReviewRequestDto;
import org.example.outlivryteamproject.domain.review.dto.responseDto.CreateReviewResponseDto;
import org.example.outlivryteamproject.domain.review.dto.responseDto.FindReviewResponseDto;
import org.example.outlivryteamproject.domain.review.dto.responseDto.UpdateReviewResponseDto;
import org.springframework.data.domain.Page;

public interface ReviewService {

    CreateReviewResponseDto save(Long userId, Long storeId, Long orderId, CreateReviewRequestDto requestDto);

    Page<FindReviewResponseDto> findAll(Long storeId, int page);

    Page<FindReviewResponseDto> findByStars(Long storeId, int page, int start, int end);

    UpdateReviewResponseDto update(Long userId, Long reviewId, UpdateReviewRequestDto requestDto);

    void delete(Long userId, Long reviewId);
}
