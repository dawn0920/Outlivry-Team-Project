package org.example.outlivryteamproject.domain.review.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.example.outlivryteamproject.common.BaseEntity;
import org.example.outlivryteamproject.domain.review.dto.requestDto.CreateReviewRequestDto;
import org.example.outlivryteamproject.domain.review.dto.requestDto.UpdateReviewRequestDto;
import org.example.outlivryteamproject.domain.store.entity.Store;
import org.example.outlivryteamproject.domain.user.entity.User;
import org.example.outlivryteamproject.exception.CustomException;
import org.example.outlivryteamproject.exception.ExceptionCode;

@Entity
@Getter
@Table(name = "review")
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private Integer stars;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Review() {

    }

    public Review(User user, Store store, CreateReviewRequestDto requestDto) {
        this.user = user;
        this.store = store;
        this.contents = requestDto.getContents();
        this.stars = requestDto.getStars();
    }

    public void update(UpdateReviewRequestDto requestDto) {

        if (requestDto.getContents() == null && requestDto.getStars() == null) {
            throw new CustomException(ExceptionCode.INVALID_REVIEW_UPDATE);
        }

        if (requestDto.getContents() != null) {
            this.contents = requestDto.getContents();
        }

        if (requestDto.getStars() != null) {
            this.stars = requestDto.getStars();
        }
    }
}

