package org.example.outlivryteamproject.domain.review.service;

import lombok.RequiredArgsConstructor;
import org.example.outlivryteamproject.domain.order.entity.Order;
import org.example.outlivryteamproject.domain.order.repository.OrderRepository;
import org.example.outlivryteamproject.domain.review.dto.requestDto.CreateReviewRequestDto;
import org.example.outlivryteamproject.domain.review.dto.requestDto.UpdateReviewRequestDto;
import org.example.outlivryteamproject.domain.review.dto.responseDto.CreateReviewResponseDto;
import org.example.outlivryteamproject.domain.review.dto.responseDto.FindReviewResponseDto;
import org.example.outlivryteamproject.domain.review.dto.responseDto.UpdateReviewResponseDto;
import org.example.outlivryteamproject.domain.review.entity.Review;
import org.example.outlivryteamproject.domain.review.repository.ReviewRepository;
import org.example.outlivryteamproject.domain.store.entity.Store;
import org.example.outlivryteamproject.domain.store.repository.StoreRepository;
import org.example.outlivryteamproject.domain.user.entity.User;
import org.example.outlivryteamproject.domain.user.repository.UserRepository;
import org.example.outlivryteamproject.exception.CustomException;
import org.example.outlivryteamproject.exception.ExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public CreateReviewResponseDto save(Long userId, Long storeId, Long orderId, CreateReviewRequestDto requestDto) {

        User findedUser = userRepository.findByIdOrElseThrow(userId);
        Store findedStore = storeRepository.findByStoreIdOrElseThrow(storeId);
        Order findedOrder = orderRepository.findByOrderIdOrElseThrow(orderId);

        //주문이 완료되지 않았을경우 예외처리
        if (!findedOrder.isDelivery()) {
            throw new CustomException(ExceptionCode.REVIEW_NOT_ALLOWED_BEFORE_ORDER_COMPLETION);
        }

        //이미 리뷰를 작성한 경우 예외처리
        if (reviewRepository.findByOrder(findedOrder) != null) {
            throw new CustomException(ExceptionCode.EXIST_REVIEW);
        }

        Review review = new Review(findedUser, findedStore, findedOrder, requestDto);
        Review savedReview = reviewRepository.save(review);

        return new CreateReviewResponseDto(savedReview);
    }

    @Override
    public Page<FindReviewResponseDto> findAll(Long storeId, int page) {

        Store findedStore = storeRepository.findByStoreIdOrElseThrow(storeId);

        int adjustedPage = (page > 0) ? page - 1 : 0;
        PageRequest pageable = PageRequest.of(adjustedPage, 10, Sort.by("createTime").descending());
        Page<Review> reviews = reviewRepository.findByStore(findedStore, pageable);

        return reviews.map(FindReviewResponseDto::new);
    }

    @Override
    public Page<FindReviewResponseDto> findByStars(Long storeId, int page, int start, int end) {

        Store findedStore = storeRepository.findByStoreIdOrElseThrow(storeId);

        int adjustPage = (page > 0) ? page - 1 : 0;
        PageRequest pageable = PageRequest.of(adjustPage, 10, Sort.by("createTime").descending());

        Page<Review> reviews = reviewRepository.findByStoreAndStarsBetween(findedStore, start, end, pageable);

        return reviews.map(FindReviewResponseDto::new);

    }

    @Override
    @Transactional
    public UpdateReviewResponseDto update(Long userId, Long reviewId, UpdateReviewRequestDto requestDto) {

        User findedUser = userRepository.findByIdOrElseThrow(userId);
        Review findedReview = reviewRepository.findByReviewIdOrElseThrow(reviewId);

        //로그인 한 유저와 리뷰를 작성한 유저가 다를 경우 예외처리
        if (!findedReview.getUser().equals(findedUser)) {
            throw new CustomException(ExceptionCode.REVIEW_ACCESS_DENIED);
        }

        findedReview.update(requestDto);

        Review savedReview = reviewRepository.save(findedReview);

        return new UpdateReviewResponseDto(savedReview);
    }

    @Override
    @Transactional
    public void delete(Long userId, Long reviewId) {

        User findedUser = userRepository.findByIdOrElseThrow(userId);
        Review findedReview = reviewRepository.findByReviewIdOrElseThrow(reviewId);

        //로그인 한 유저와 리뷰를 작성한 유저가 다를 경우 예외처리
        if (!findedReview.getUser().equals(findedUser)) {
            throw new CustomException(ExceptionCode.REVIEW_ACCESS_DENIED);
        }

        reviewRepository.delete(findedReview);
    }
}
