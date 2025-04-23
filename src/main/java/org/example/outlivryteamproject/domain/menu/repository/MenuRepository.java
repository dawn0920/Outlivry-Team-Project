package org.example.outlivryteamproject.domain.menu.repository;

import org.example.outlivryteamproject.domain.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
