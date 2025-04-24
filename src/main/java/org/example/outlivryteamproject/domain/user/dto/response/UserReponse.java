package org.example.outlivryteamproject.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.outlivryteamproject.domain.user.entity.User;

@Getter
public class UserReponse {

    private final Long id;
    private final String email;
    private final String nickname;
    private final String name;
    private final String phone;
    private final String address;
    private final String birth;

    public UserReponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.name = user.getName();
        this.phone = user.getPhone();
        this.address = user.getAddress();
        this.birth = user.getBirth();
    }
}
