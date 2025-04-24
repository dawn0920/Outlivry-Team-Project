package org.example.outlivryteamproject.domain.review.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.outlivryteamproject.common.response.ApiResponse;
import org.example.outlivryteamproject.domain.review.dto.requestDto.CreateReviewRequestDto;
import org.example.outlivryteamproject.domain.review.dto.requestDto.FindByStarsRequestDto;
import org.example.outlivryteamproject.domain.review.dto.requestDto.UpdateReviewRequestDto;
import org.example.outlivryteamproject.domain.review.dto.responseDto.CreateReviewResponseDto;
import org.example.outlivryteamproject.domain.review.dto.responseDto.FindReviewResponseDto;
import org.example.outlivryteamproject.domain.review.dto.responseDto.UpdateReviewResponseDto;
import org.example.outlivryteamproject.domain.review.service.ReviewServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewServiceImpl reviewService;

    @PostMapping("/stores/{storeId}")
    public ResponseEntity<ApiResponse<CreateReviewResponseDto>> createReview(
            @PathVariable Long storeId,
            @Valid @RequestBody CreateReviewRequestDto requestDto
    ) {
        CreateReviewResponseDto createdReview = reviewService.save(storeId, requestDto);

        return new ResponseEntity<>(new ApiResponse<>("리뷰를 작성했습니다.", createdReview), HttpStatus.CREATED);
    }

    @GetMapping("/stores/{storeId}")
    public ResponseEntity<ApiResponse<Page<FindReviewResponseDto>>> findAll(
            @PathVariable Long storeId,
            @RequestParam(defaultValue = "1") int page
    ) {
        Page<FindReviewResponseDto> reviews = reviewService.findAll(storeId, page);

        return new ResponseEntity<>(new ApiResponse<>("리뷰를 조회합니다.",reviews), HttpStatus.OK);
    }

    @GetMapping("/stores/{storeId}/stars")
    public ResponseEntity<ApiResponse<Page<FindReviewResponseDto>>> findByStars(
            @PathVariable Long storeId,
            @RequestParam(defaultValue = "1") int page,
            @Valid @RequestBody FindByStarsRequestDto requestDto
    ) {
        Page<FindReviewResponseDto> reviews = reviewService.findByStars(storeId, page, requestDto);

        return new ResponseEntity<>(new ApiResponse<>("별점으로 리뷰를 조회합니다.",reviews), HttpStatus.OK);
    }

    @PatchMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<UpdateReviewResponseDto>> updateReview(
            @PathVariable Long reviewId,
            @Valid @RequestBody UpdateReviewRequestDto requestDto
    ) {
        UpdateReviewResponseDto updatedReview = reviewService.update(reviewId, requestDto);

        return new ResponseEntity<>(new ApiResponse<>("리뷰를 수정했습니다.",updatedReview), HttpStatus.OK);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<Void>> deleteReview(
            @PathVariable Long reviewId
    ) {
        reviewService.delete(reviewId);

        return new ResponseEntity<>(new ApiResponse<>("리뷰를 삭제했습니다."),HttpStatus.NO_CONTENT);
    }

}
