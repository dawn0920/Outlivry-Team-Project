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
public class ModifiedMenuRequestDto {

    private String menuName;

    private Integer price;

    private MultipartFile image;
}
