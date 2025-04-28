package org.example.outlivryteamproject.domain.review.dto.requestDto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateReviewRequestDto {

    @Nullable
    @Size(max = 200, message = "최대 글자수는 200글자입니다")
    private String contents;

    @Nullable
    @Min(value = 1, message = "별점은 최소 1점입니다.")
    @Max(value = 5, message = "별점은 5점을 넘을 수 없습니다.")
    private Integer stars;

    public UpdateReviewRequestDto() {

    }

    public UpdateReviewRequestDto(String contents, Integer stars) {
        this.contents = contents;
        this.stars = stars;
    }
}
