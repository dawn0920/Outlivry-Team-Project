package org.example.outlivryteamproject.common;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@MappedSuperclass
public abstract class StoreDelete {

    // storedeletd를 baseEntity를 상속하여 전역에 storedeletd 생성,
    // @SQLDelete와 @Where을 이용해서 소프트 딜리트 사용
    // 사용법은 storeEntity 참고

    @Column(name = "store_deleted")
    private boolean storeDeleted;

    public boolean storeDeleted() {
        return storeDeleted;
    }

}
