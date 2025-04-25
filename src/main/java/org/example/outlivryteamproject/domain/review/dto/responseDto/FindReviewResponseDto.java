package org.example.outlivryteamproject.domain.review.dto.responseDto;

import lombok.Getter;
import org.example.outlivryteamproject.domain.review.entity.Review;

import java.time.LocalDateTime;

@Getter
public class FindReviewResponseDto {

    private final Long id;

    private final String userNickname;

    private final String contents;

    private final Integer stars;

    private final LocalDateTime creatTime;

    private final LocalDateTime modifiedTime;

    public FindReviewResponseDto(Review review) {
        this.id = review.getReviewId();
        this.userNickname = review.getUser().getNickname();
        this.contents = review.getContents();
        this.stars = review.getStars();
        this.creatTime = review.getCreatTime();
        this.modifiedTime = review.getModifiedTime();
    }
}
