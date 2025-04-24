package org.example.outlivryteamproject.domain.menu.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MenuRequestDto {

    private String menuName;

    private Integer price;

    private MultipartFile image;

    private Boolean isDepleted;

    private Boolean isDeleted;
}
