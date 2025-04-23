package org.example.outlivryteamproject.domain.review.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.example.outlivryteamproject.common.BaseEntity;
import org.example.outlivryteamproject.domain.review.dto.requestDto.CreateReviewRequestDto;
import org.example.outlivryteamproject.domain.review.dto.requestDto.UpdateReviewRequestDto;
import org.example.outlivryteamproject.domain.store.entity.Store;

@Entity
@Getter
@Table(name = "reviews")
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private Integer stars;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    public Review() {

    }

    public Review(Store store, CreateReviewRequestDto requestDto) {
        this.store = store;
        this.contents = requestDto.getContents();
        this.stars = requestDto.getStars();
    }

    public void update(UpdateReviewRequestDto requestDto) {
        if (requestDto.getContents() == null && requestDto.getStars() == null) {
            throw new NullPointerException("내용과 별점 중 최소 하나는 수정해야합니다.");
        }

        if (requestDto.getContents().isBlank()) {
            throw new IllegalArgumentException("내용을 입력해주세요.");
        }

        if (requestDto.getContents() != null && !requestDto.getContents().isBlank()) {
            this.contents = requestDto.getContents();
        }

        if (requestDto.getStars() != null) {
            this.stars = requestDto.getStars();
        }
    }
}

