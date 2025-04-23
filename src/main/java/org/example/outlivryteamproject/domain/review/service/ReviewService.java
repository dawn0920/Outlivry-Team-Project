package org.example.outlivryteamproject.domain.review.service;

import org.example.outlivryteamproject.domain.review.dto.requestDto.CreateReviewRequestDto;
import org.example.outlivryteamproject.domain.review.dto.requestDto.FindByStarsRequestDto;
import org.example.outlivryteamproject.domain.review.dto.requestDto.UpdateReviewRequestDto;
import org.example.outlivryteamproject.domain.review.dto.responseDto.CreateReviewResponseDto;
import org.example.outlivryteamproject.domain.review.dto.responseDto.FindReviewResponseDto;
import org.example.outlivryteamproject.domain.review.dto.responseDto.UpdateReviewResponseDto;
import org.springframework.data.domain.Page;

public interface ReviewService {

    CreateReviewResponseDto save(Long storeId, CreateReviewRequestDto requestDto);

    Page<FindReviewResponseDto> findAll(Long storeId, int page);

    Page<FindReviewResponseDto> findByStars(Long storeId, int page, FindByStarsRequestDto requestDto);

    UpdateReviewResponseDto update(Long reviewId, UpdateReviewRequestDto requestDto);

    void delete(Long reviewId);
}
