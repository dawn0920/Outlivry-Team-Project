package org.example.outlivryteamproject.domain.review.dto.requestDto;

import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class CreateReviewRequestDto {

    @NotBlank
    @Size(max = 200, message = "최대 글자수는 200글자입니다")
    private String contents;

    @NotNull
    @Min(value = 1, message = "별점은 최소 1점입니다.")
    @Max(value = 5, message = "별점은 5점을 넘을 수 없습니다.")
    private Integer stars;

    public CreateReviewRequestDto() {
    }

    public CreateReviewRequestDto(String contents, Integer stars) {
        this.contents = contents;
        this.stars = stars;
    }

}
