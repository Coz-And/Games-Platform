package com.connectedgames.repository;

import com.connectedgames.model.Gioco;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GiocoRepository extends JpaRepository<Gioco, Long> {
    List<Gioco> findByLocaleId(Long localeId);
    List<Gioco> findByTipo(Gioco.TipoGioco tipo);
}