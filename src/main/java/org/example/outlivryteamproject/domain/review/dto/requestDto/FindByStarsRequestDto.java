package org.example.outlivryteamproject.domain.review.dto.requestDto;

import lombok.Getter;

@Getter
public class FindByStarsRequestDto {

    private final Integer start;

    private final Integer end;

    public FindByStarsRequestDto(Integer start, Integer end) {
        this.start = start;
        this.end = end;
    }
}
