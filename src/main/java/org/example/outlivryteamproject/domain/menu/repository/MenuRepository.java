package org.example.outlivryteamproject.domain.menu.repository;

import org.example.outlivryteamproject.domain.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    Optional<Menu> findByMenuIdAndStatusNot(Long id, Integer status);
    default Menu findMenuByIdOrElseThrow(Long id) {
        return findByMenuIdAndStatusNot(id, 0)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "메뉴를 찾을 수 없습니다."));
    }
}
