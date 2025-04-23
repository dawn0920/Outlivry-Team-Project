package org.example.outlivryteamproject.domain.review.service;

import org.example.outlivryteamproject.domain.review.dto.requestDto.CreateReviewRequestDto;
import org.example.outlivryteamproject.domain.review.dto.requestDto.FindByStarsRequestDto;
import org.example.outlivryteamproject.domain.review.dto.responseDto.CreateReviewResponseDto;
import org.example.outlivryteamproject.domain.review.dto.responseDto.FindReviewResponseDto;
import org.springframework.data.domain.Page;

public interface ReviewService {

    CreateReviewResponseDto save(CreateReviewRequestDto requestDto);

    Page<FindReviewResponseDto> findAll(int page);

    Page<FindReviewResponseDto> findByStars(int page, FindByStarsRequestDto requestDto);
}
