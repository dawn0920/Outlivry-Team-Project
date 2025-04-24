package org.example.outlivryteamproject.domain.menu.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class MenuRequestDto {

    @Setter
    private Long userId;

    @Setter
    private Long storeId;

    private String menuName;

    private Integer price;

    private MultipartFile image;

    private Boolean status;

    private Boolean isDepleted;

    private Boolean isDeleted;
}
