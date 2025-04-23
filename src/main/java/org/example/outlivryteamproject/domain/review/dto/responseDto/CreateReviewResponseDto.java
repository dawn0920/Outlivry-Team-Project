package org.example.outlivryteamproject.domain.review.dto.responseDto;

import lombok.Getter;
import org.example.outlivryteamproject.domain.review.entity.Review;

import java.time.LocalDateTime;

@Getter
public class CreateReviewResponseDto {

    private final Long id;

    private final String contents;

    private final Integer stars;

    private final LocalDateTime creatTime;

    public CreateReviewResponseDto(Review review) {
        this.id = review.getReviewId();
        this.contents = review.getContents();
        this.stars = review.getStars();
        this.creatTime = review.getCreatTime();
    }
}
