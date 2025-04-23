package org.example.outlivryteamproject.domain.review.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.example.outlivryteamproject.common.BaseEntity;
import org.example.outlivryteamproject.domain.review.dto.requestDto.CreateReviewRequestDto;
import org.example.outlivryteamproject.domain.review.dto.requestDto.UpdateReviewRequestDto;

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

    public Review() {

    }

    public Review(CreateReviewRequestDto requestDto) {
        this.contents = requestDto.getContents();
        this.stars = requestDto.getStars();
    }

    public void update(UpdateReviewRequestDto requestDto) {
        this.contents = requestDto.getContents();
        this.stars = requestDto.getStars();
    }
}

