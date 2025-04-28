package org.example.outlivryteamproject.domain.review.dto.responseDto;

import lombok.Getter;
import org.example.outlivryteamproject.domain.review.entity.Review;

import java.time.LocalDateTime;

@Getter
public class CreateReviewResponseDto {

    private Long reviewId;

    private String userNickname;

    private String contents;

    private Integer stars;

    private LocalDateTime createTime;

    public CreateReviewResponseDto(Review review) {
        this.reviewId = review.getReviewId();
        this.userNickname = review.getUser().getNickname();
        this.contents = review.getContents();
        this.stars = review.getStars();
        this.createTime = review.getCreateTime();
    }
}
