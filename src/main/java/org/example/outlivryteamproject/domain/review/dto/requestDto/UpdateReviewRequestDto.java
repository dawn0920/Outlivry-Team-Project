package org.example.outlivryteamproject.domain.review.dto.requestDto;

import lombok.Getter;

@Getter
public class UpdateReviewRequestDto {

    private final String contents;

    private final Integer stars;

    public UpdateReviewRequestDto(String contents, Integer stars) {
        this.contents = contents;
        this.stars = stars;
    }
}
