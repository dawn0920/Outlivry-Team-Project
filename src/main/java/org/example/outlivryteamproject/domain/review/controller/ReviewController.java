package org.example.outlivryteamproject.domain.review.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/store/{storeId}/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewServiceImpl reviewService;

    @PostMapping
    public ResponseEntity<CreateReviewResponseDto> createReview(
            @PathVariable Long storeId,
            @Valid @RequestBody CreateReviewRequestDto requestDto
    ) {
        CreateReviewResponseDto createdReview = reviewService.save(storeId, requestDto);

        return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<FindReviewResponseDto>> findAll(
            @PathVariable Long storeId,
            @RequestParam(defaultValue = "1") int page
    ) {
        Page<FindReviewResponseDto> reviews = reviewService.findAll(storeId, page);

        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/stars")
    public ResponseEntity<Page<FindReviewResponseDto>> findByStars(
            @PathVariable Long storeId,
            @RequestParam(defaultValue = "1") int page,
            @Valid @RequestBody FindByStarsRequestDto requestDto
    ) {
        Page<FindReviewResponseDto> reviews = reviewService.findByStars(storeId, page, requestDto);

        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @PatchMapping("/{reviewId}")
    public ResponseEntity<UpdateReviewResponseDto> updateReview(
            @PathVariable Long storeId,
            @PathVariable Long reviewId,
            @Valid @RequestBody UpdateReviewRequestDto requestDto
    ) {
        UpdateReviewResponseDto updatedReview = reviewService.update(reviewId, requestDto);

        return new ResponseEntity<>(updatedReview, HttpStatus.OK);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(
            @PathVariable Long storeId,
            @PathVariable Long reviewId
    ) {
        reviewService.delete(reviewId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
