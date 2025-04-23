package org.example.outlivryteamproject.domain.review.dto.requestDto;

import lombok.Getter;

@Getter
public class CreateReviewRequestDto {

    private final String contents;

    private final Integer stars;

    public CreateReviewRequestDto(String contents, Integer stars) {
        this.contents = contents;
        this.stars = stars;
    }
}
