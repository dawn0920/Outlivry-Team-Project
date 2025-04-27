package org.example.outlivryteamproject.domain.review.controller;

import org.example.outlivryteamproject.common.TokenUserId;
import org.example.outlivryteamproject.domain.order.entity.Order;
import org.example.outlivryteamproject.domain.review.dto.requestDto.CreateReviewRequestDto;
import org.example.outlivryteamproject.domain.review.dto.responseDto.CreateReviewResponseDto;
import org.example.outlivryteamproject.domain.review.entity.Review;
import org.example.outlivryteamproject.domain.review.service.ReviewServiceImpl;
import org.example.outlivryteamproject.domain.store.entity.Store;
import org.example.outlivryteamproject.domain.user.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReviewController.class)
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    ReviewServiceImpl reviewService;

    @MockitoBean
    private TokenUserId tokenUserId;

    @Test
    void 리뷰_생성_성공_테스트() throws Exception {
        //given
        User user = new User();
        Store store = new Store();
        Order order = new Order();

        CreateReviewRequestDto requestDto = new CreateReviewRequestDto("리뷰입니다", 3);

        user.setId(1L);
        user.setNickname("name");
        store.setStoreId(1L);
        order.setOrderId(1L);

        Review review = new Review(user, store, order, requestDto);

        given(reviewService.save(user.getId(), store.getStoreId(), order.getOrderId(), requestDto))
                .willReturn(new CreateReviewResponseDto(review));

        //when
        mockMvc.perform(post("/stores/{storeId}/orders/{orderId}"))
                //then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userNickName").value(equalTo(user.getNickname())))
                .andExpect(jsonPath("$.contents").value(equalTo(review.getContents())))
                .andExpect(jsonPath("$.stars").value(equalTo(review.getStars())));
    }
}