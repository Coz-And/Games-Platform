package com.connectedgames.repository;

import com.connectedgames.model.EventoGioco;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EventoGiocoRepository extends JpaRepository<EventoGioco, Long> {
    List<EventoGioco> findByGiocoId(String giocoId);
    List<EventoGioco> findByLocaleId(String localeId);
}
