package com.connectedgames.repository;

import com.connectedgames.model.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {
    List<Prenotazione> findByUtenteId(Long utenteId);
    List<Prenotazione> findByLocaleId(Long localeId);
}
