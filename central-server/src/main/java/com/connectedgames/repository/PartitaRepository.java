package com.connectedgames.repository;

import com.connectedgames.model.Partita;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PartitaRepository extends JpaRepository<Partita, Long> {
    List<Partita> findByGiocoId(Long giocoId);
    List<Partita> findByStato(Partita.StatoPartita stato);
    List<Partita> findByGiocatoriId(Long utenteId);
    List<Partita> findByTorneoId(Long torneoId);
}