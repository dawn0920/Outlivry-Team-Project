package org.example.outlivryteamproject.domain.review.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.outlivryteamproject.common.TokenUserId;
import org.example.outlivryteamproject.domain.order.entity.Order;
import org.example.outlivryteamproject.domain.review.dto.requestDto.CreateReviewRequestDto;
import org.example.outlivryteamproject.domain.review.dto.requestDto.UpdateReviewRequestDto;
import org.example.outlivryteamproject.domain.review.dto.responseDto.CreateReviewResponseDto;
import org.example.outlivryteamproject.domain.review.dto.responseDto.FindReviewResponseDto;
import org.example.outlivryteamproject.domain.review.dto.responseDto.UpdateReviewResponseDto;
import org.example.outlivryteamproject.domain.review.entity.Review;
import org.example.outlivryteamproject.domain.review.service.ReviewServiceImpl;
import org.example.outlivryteamproject.domain.store.entity.Store;
import org.example.outlivryteamproject.domain.user.entity.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReviewController.class)
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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

        Long storeId = 1L;
        Long orderId = 1L;

        user.setId(1L);

        Review review = new Review(user, store, order, requestDto);

        given(reviewService.save(any(),any(),any(),any()))
                .willReturn(new CreateReviewResponseDto(review));

        //when
        mockMvc.perform(post("/reviews/stores/{storeId}/orders/{orderId}", storeId, orderId)
                        .header("Authorization", "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                //then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.contents.userNickname").value(equalTo(user.getNickname())))
                .andExpect(jsonPath("$.contents.contents").value(equalTo(review.getContents())))
                .andExpect(jsonPath("$.contents.stars").value(equalTo(review.getStars())));
    }

    @Test
    void 리뷰_조회_성공_테스트() throws Exception {
        //given
        Long storeId = 1L;
        int page = 1;

        Review review = Mockito.mock(Review.class);
        User user = Mockito.mock(User.class);
        Store store = Mockito.mock(Store.class);
        Order order = Mockito.mock(Order.class);

        given(review.getReviewId()).willReturn(1L);
        given(review.getContents()).willReturn("리뷰 내용");
        given(review.getStars()).willReturn(5);
        given(review.getStore()).willReturn(store);
        given(review.getUser()).willReturn(user);
        given(review.getOrder()).willReturn(order);

        given(user.getNickname()).willReturn("nickname");
        given(store.getStoreId()).willReturn(storeId);

        FindReviewResponseDto responseDto = new FindReviewResponseDto(review);
        Page<FindReviewResponseDto> pageResponse = new PageImpl<>(List.of(responseDto));

        given(reviewService.findAll(storeId, page)).willReturn(pageResponse);

        //when
        mockMvc.perform(get("/reviews/stores/{storeId}", storeId))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contents.content[0].userNickname").value(equalTo(user.getNickname())))
                .andExpect(jsonPath("$.contents.content[0].contents").value(equalTo(review.getContents())))
                .andExpect(jsonPath("$.contents.content[0].stars").value(equalTo(review.getStars())));
    }

    @Test
    void 별점순으로_조회_성공_테스트() throws Exception {
        //given
        Long storeId = 1L;
        int page = 1;
        int start = 1;
        int end = 3;

        Review review = Mockito.mock(Review.class);
        User user = Mockito.mock(User.class);
        Store store = Mockito.mock(Store.class);
        Order order = Mockito.mock(Order.class);

        given(review.getReviewId()).willReturn(1L);
        given(review.getContents()).willReturn("리뷰 내용");
        given(review.getStars()).willReturn(5);
        given(review.getStore()).willReturn(store);
        given(review.getUser()).willReturn(user);
        given(review.getOrder()).willReturn(order);

        given(user.getNickname()).willReturn("nickname");
        given(store.getStoreId()).willReturn(storeId);

        FindReviewResponseDto responseDto = new FindReviewResponseDto(review);
        Page<FindReviewResponseDto> pageResponse = new PageImpl<>(List.of(responseDto));

        given(reviewService.findByStars(storeId, page, start, end)).willReturn(pageResponse);

        //when
        mockMvc.perform(get("/reviews/stores/{storeId}/stars?page=1&start=1&end=3", storeId))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contents.content[0].userNickname").value(equalTo(user.getNickname())))
                .andExpect(jsonPath("$.contents.content[0].contents").value(equalTo(review.getContents())))
                .andExpect(jsonPath("$.contents.content[0].stars").value(equalTo(review.getStars())));

    }

//    @Test
//    void 리뷰_수정_성공_테스트() throws Exception{
//        //given
//        Review review = Mockito.mock(Review.class);
//        User user = Mockito.mock(User.class);
//        Store store = Mockito.mock(Store.class);
//        Order order = Mockito.mock(Order.class);
//
//        given(user.getId()).willReturn(1L);
//        given(user.getNickname()).willReturn("nickname");
//        given(store.getStoreId()).willReturn(1L);
//
//        given(review.getReviewId()).willReturn(1L);
//        given(review.getContents()).willReturn("리뷰 내용");
//        given(review.getStars()).willReturn(5);
//        given(review.getStore()).willReturn(store);
//        given(review.getUser()).willReturn(user);
//        given(review.getOrder()).willReturn(order);
//
//        UpdateReviewRequestDto requestDto = new UpdateReviewRequestDto("수정", 1);
//        UpdateReviewResponseDto responseDto = new UpdateReviewResponseDto(review);
//
//        given(reviewService.update(user.getId(), review.getReviewId(), requestDto))
//                .willReturn(responseDto);
//
//        //when
//        mockMvc.perform(patch("/reviews/{reviewId}", review.getReviewId())
//                        .header("Authorization", "Bearer token")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(requestDto)))
//                //then
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.contents.userNickname").value(equalTo(user.getNickname())))
//                .andExpect(jsonPath("$.contents.contents").value(equalTo(requestDto.getContents())))
//                .andExpect(jsonPath("$.contents.stars").value(equalTo(requestDto.getStars())));
//    }
}