package com.connectedgames.repository;

import com.connectedgames.model.Torneo;
import com.connectedgames.model.Gioco;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TorneoRepository extends JpaRepository<Torneo, Long> {
    List<Torneo> findByTipoGioco(Gioco.TipoGioco tipo);
    List<Torneo> findByStato(Torneo.StatoTorneo stato);
}