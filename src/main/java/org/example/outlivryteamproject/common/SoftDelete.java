package org.example.outlivryteamproject.common;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@MappedSuperclass
public abstract class SoftDelete {

    // isDeletd를 baseEntity를 상속하여, baseEntity 상속 받은 entity에 isDeletd 생성,
    // @SQLDelete와 @Where을 이용해서 소프트 딜리트 사용
    // 사용법은 store Entity 참고

    @Column(name = "is_deleted")
    private boolean isDeleted;

    public void softDelete() {
        this.isDeleted = true;
    }
}
