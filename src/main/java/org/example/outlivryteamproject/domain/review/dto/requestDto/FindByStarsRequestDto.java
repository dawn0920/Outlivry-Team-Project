package org.example.outlivryteamproject.domain.review.dto.requestDto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class FindByStarsRequestDto {

    @NotNull
    @Min(value = 1, message = "별점은 최소 1점입니다.")
    @Max(value = 5, message = "별점은 5점을 넘을 수 없습니다.")
    private final Integer start;

    @NotNull
    @Min(value = 1, message = "별점은 최소 1점입니다.")
    @Max(value = 5, message = "별점은 5점을 넘을 수 없습니다.")
    private final Integer end;

    public FindByStarsRequestDto(Integer start, Integer end) {
        this.start = start;
        this.end = end;
    }
}
