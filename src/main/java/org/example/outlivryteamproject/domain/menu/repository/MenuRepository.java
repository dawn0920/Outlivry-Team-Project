package org.example.outlivryteamproject.domain.menu.repository;

import org.example.outlivryteamproject.domain.menu.entity.Menu;
import org.example.outlivryteamproject.domain.store.entity.Store;
import org.example.outlivryteamproject.exception.CustomException;
import org.example.outlivryteamproject.exception.ExceptionCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findAllByStore(Store store);
    Optional<Menu> findByMenuId(Long id);
    default Menu findMenuByIdOrElseThrow(Long id) {
        return findByMenuId(id)
                .orElseThrow(() -> new CustomException(ExceptionCode.MENU_NOT_FOUND));
    }
}
