package org.example.outlivryteamproject.domain.store.dto.response;


import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.outlivryteamproject.domain.menu.dto.responseDto.MenuResponseDto;
import org.example.outlivryteamproject.domain.store.entity.Store;

@Getter
@RequiredArgsConstructor
public class findOneStoreResponseDto extends StoreResponseDto{

    private List<MenuResponseDto> menuList;

    private Double stars;

    public findOneStoreResponseDto(Store store, Double stars) {
        super(store);
        this.menuList = store.getMenuList().stream().map(MenuResponseDto::new).collect(Collectors.toList());
        this.stars = stars;
    }
}
