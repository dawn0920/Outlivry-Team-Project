package org.example.outlivryteamproject.domain.menu.dto.requestDto;

import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    private String menuName;

    @NotBlank
    private Integer price;

    private MultipartFile image;

//    private Boolean isDepleted;
//
//    private Boolean isDeleted;
}
