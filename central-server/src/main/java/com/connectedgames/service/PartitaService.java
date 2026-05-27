package com.connectedgames.service;

import com.connectedgames.model.Partita;
import com.connectedgames.repository.PartitaRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PartitaService {

    private final PartitaRepository partitaRepository;

    public PartitaService(PartitaRepository partitaRepository) {
        this.partitaRepository = partitaRepository;
    }

    public List<Partita> findAll() {
        return partitaRepository.findAll();
    }

    public Partita findById(Long id) {
        return partitaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partita non trovata con id: " + id));
    }

    public List<Partita> findByGiocatore(Long utenteId) {
        return partitaRepository.findByGiocatoriId(utenteId);
    }

    public Partita iniziaPartita(Partita partita) {
        partita.setIniziata(LocalDateTime.now());
        partita.setStato(Partita.StatoPartita.IN_CORSO);
        return partitaRepository.save(partita);
    }

    public Partita terminaPartita(Long id) {
        Partita partita = findById(id);
        partita.setTerminata(LocalDateTime.now());
        partita.setStato(Partita.StatoPartita.TERMINATA);
        return partitaRepository.save(partita);
    }

    public void delete(Long id) {
        partitaRepository.deleteById(id);
    }
}