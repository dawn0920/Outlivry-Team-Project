package org.example.outlivryteamproject.domain.review.service;

import org.example.outlivryteamproject.domain.review.dto.requestDto.CreateReviewRequestDto;
import org.example.outlivryteamproject.domain.review.dto.responseDto.CreateReviewResponseDto;
import org.example.outlivryteamproject.domain.review.dto.responseDto.FindResponseDto;
import org.springframework.data.domain.Page;

public interface ReviewService {

    public CreateReviewResponseDto save(CreateReviewRequestDto requestDto);

    public Page<FindResponseDto> findAll(int page);
}
