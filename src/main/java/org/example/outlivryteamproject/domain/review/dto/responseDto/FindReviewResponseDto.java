package org.example.outlivryteamproject.domain.review.dto.responseDto;

import lombok.Getter;
import org.example.outlivryteamproject.domain.review.entity.Review;

import java.time.LocalDateTime;

@Getter
public class FindReviewResponseDto {

    private Long id;

    private String userNickname;

    private String contents;

    private Integer stars;

    private LocalDateTime createTime;

    private LocalDateTime modifiedTime;

    public FindReviewResponseDto(Review review) {
        this.id = review.getReviewId();
        this.userNickname = review.getUser().getNickname();
        this.contents = review.getContents();
        this.stars = review.getStars();
        this.createTime = review.getCreateTime();
        this.modifiedTime = review.getModifiedTime();
    }
}
