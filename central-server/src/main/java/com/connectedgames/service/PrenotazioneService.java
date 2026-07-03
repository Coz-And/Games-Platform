package com.connectedgames.service;

import com.connectedgames.model.Prenotazione;
import com.connectedgames.repository.PrenotazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PrenotazioneService {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    public List<Prenotazione> findAll() {
        return prenotazioneRepository.findAll();
    }

    public Prenotazione findById(Long id) {
        return prenotazioneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prenotazione non trovata con id: " + id));
    }

    public List<Prenotazione> findByUtente(Long utenteId) {
        return prenotazioneRepository.findByUtenteId(utenteId);
    }

    public Prenotazione crea(Prenotazione prenotazione) {
        prenotazione.setStato(Prenotazione.StatoPrenotazione.CONFERMATA);
        prenotazione.setCreata(LocalDateTime.now());
        return prenotazioneRepository.save(prenotazione);
    }

    public Prenotazione annulla(Long id) {
        Prenotazione prenotazione = findById(id);
        prenotazione.setStato(Prenotazione.StatoPrenotazione.ANNULLATA);
        return prenotazioneRepository.save(prenotazione);
    }
}
