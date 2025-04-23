package org.example.outlivryteamproject.domain.menu.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

@Getter
@AllArgsConstructor
public class MenuRequestDto {

    @Setter
    private Long userId;

    @Setter
    private Long storeId;

    private String menuName;

    private Integer price;

    private String image;

    @Min(1)
    @Max(2)
    private Integer status;
}
