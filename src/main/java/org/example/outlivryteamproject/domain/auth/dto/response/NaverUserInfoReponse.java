package org.example.outlivryteamproject.domain.auth.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NaverUserInfoReponse {
    private String email;
    private String name;
    private String nickname;
    private String birth;
    private String phone;
}
