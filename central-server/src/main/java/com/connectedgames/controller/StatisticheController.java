package com.connectedgames.controller;

import com.connectedgames.model.Partita;
import com.connectedgames.repository.GiocoRepository;
import com.connectedgames.repository.PartitaRepository;
import com.connectedgames.repository.TorneoRepository;
import com.connectedgames.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/statistiche")
public class StatisticheController {

    @Autowired private PartitaRepository partitaRepository;
    @Autowired private UtenteRepository utenteRepository;
    @Autowired private GiocoRepository giocoRepository;
    @Autowired private TorneoRepository torneoRepository;

    @GetMapping("/globali")
    public Map<String, Object> statisticheGlobali() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalePartite", partitaRepository.count());
        stats.put("totaleUtenti", utenteRepository.count());
        stats.put("totaleGiochi", giocoRepository.count());
        stats.put("totaleTornei", torneoRepository.count());
        stats.put("partiteInCorso", partitaRepository
                .findByStato(Partita.StatoPartita.IN_CORSO).size());
        stats.put("partiteTerminate", partitaRepository
                .findByStato(Partita.StatoPartita.TERMINATA).size());
        return stats;
    }

    @GetMapping("/giocatore/{id}")
    public Map<String, Object> statisticheGiocatore(@PathVariable Long id) {
        Map<String, Object> stats = new HashMap<>();
        var partite = partitaRepository.findByGiocatoriId(id);
        stats.put("totalePartite", partite.size());
        stats.put("partiteTerminate", partite.stream()
                .filter(p -> p.getStato() == Partita.StatoPartita.TERMINATA)
                .count());
        stats.put("partiteInCorso", partite.stream()
                .filter(p -> p.getStato() == Partita.StatoPartita.IN_CORSO)
                .count());
        return stats;
    }

    @GetMapping("/gioco/{id}")
    public Map<String, Object> statisticheGioco(@PathVariable Long id) {
        Map<String, Object> stats = new HashMap<>();
        var partite = partitaRepository.findByGiocoId(id);
        stats.put("totalePartite", partite.size());
        stats.put("partiteTerminate", partite.stream()
                .filter(p -> p.getStato() == Partita.StatoPartita.TERMINATA)
                .count());
        return stats;
    }
}