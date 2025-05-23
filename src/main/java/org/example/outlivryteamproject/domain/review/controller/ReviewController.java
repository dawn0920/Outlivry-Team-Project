package org.example.outlivryteamproject.domain.review.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.outlivryteamproject.common.TokenUserId;
import org.example.outlivryteamproject.common.response.ApiResponse;
import org.example.outlivryteamproject.domain.review.dto.requestDto.CreateReviewRequestDto;
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
    private final TokenUserId tokenUserId;

    /**
     * 리뷰 작성
     *
     * @param storeId    작성하는 가게
     * @param authHeader 로그인 유저 정보
     * @param requestDto 리뷰
     * @return ResponseEntity
     */
    @PostMapping("/stores/{storeId}/orders/{orderId}")
    public ResponseEntity<ApiResponse<CreateReviewResponseDto>> createReview(
            @PathVariable("storeId") Long storeId,
            @PathVariable("orderId") Long orderId,
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody CreateReviewRequestDto requestDto
    ) {
        Long userId = tokenUserId.getTokenUserId(authHeader);

        CreateReviewResponseDto createdReview = reviewService.save(userId, storeId, orderId, requestDto);

        return new ResponseEntity<>(new ApiResponse<>("리뷰 작성", createdReview), HttpStatus.CREATED);
    }

    /**
     * 리뷰 조회
     *
     * @param storeId 특정 가게 정보
     * @param page 페이지
     * @return ResponseEntity
     */
    @GetMapping("/stores/{storeId}")
    public ResponseEntity<ApiResponse<Page<FindReviewResponseDto>>> findAll(
            @PathVariable("storeId") Long storeId,
            @RequestParam(name = "page", defaultValue = "1") int page
    ) {
        Page<FindReviewResponseDto> reviews = reviewService.findAll(storeId, page);

        return new ResponseEntity<>(new ApiResponse<>("리뷰 조회",reviews), HttpStatus.OK);
    }

    /**
     * 별점순으로 리뷰 조회
     *
     * @param storeId 특정 가게 정보
     * @param page 페이지
     * @return ResponseEntity
     */
    @GetMapping("/stores/{storeId}/stars")
    public ResponseEntity<ApiResponse<Page<FindReviewResponseDto>>> findByStars(
            @PathVariable("storeId") Long storeId,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "start") int start,
            @RequestParam(name = "end") int end
    ) {
        Page<FindReviewResponseDto> reviews = reviewService.findByStars(storeId, page, start, end);

        return new ResponseEntity<>(new ApiResponse<>("별점으로 리뷰 조회",reviews), HttpStatus.OK);
    }

    /**
     * 리뷰 수정
     *
     * @param reviewId 작성된 리뷰 정보
     * @param authHeader 로그인 유저 정보
     * @param requestDto 수정 내용
     * @return ResponseEntity
     */
    @PatchMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<UpdateReviewResponseDto>> updateReview(
            @PathVariable("reviewId") Long reviewId,
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody UpdateReviewRequestDto requestDto
    ) {
        Long userId = tokenUserId.getTokenUserId(authHeader);
        UpdateReviewResponseDto updatedReview = reviewService.update(userId, reviewId, requestDto);

        return new ResponseEntity<>(new ApiResponse<>("리뷰 수정",updatedReview), HttpStatus.OK);
    }

    /**
     * 리뷰 삭제
     *
     * @param authHeader 로그인 유저 정보
     * @param reviewId 작성된 리뷰 정보
     * @return ResponseEntity
     */
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<Void>> deleteReview(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable("reviewId") Long reviewId
    ) {
        Long userId = tokenUserId.getTokenUserId(authHeader);
        reviewService.delete(userId, reviewId);

        return new ResponseEntity<>(new ApiResponse<>("리뷰 삭제"),HttpStatus.OK);
    }

}
