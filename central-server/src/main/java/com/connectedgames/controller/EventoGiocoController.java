package com.connectedgames.controller;

import com.connectedgames.model.EventoGioco;
import com.connectedgames.repository.EventoGiocoRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Riceve gli eventi sensore che ogni edge-server inoltra periodicamente
 * (vedi SyncService lato edge-server) e li archivia sul database centrale.
 * Endpoint pubblico (vedi SecurityConfig): la sincronizzazione avviene tra
 * server, senza un utente autenticato nella richiesta.
 */
@RestController
@RequestMapping("/api/eventi")
public class EventoGiocoController {

    @Autowired
    private EventoGiocoRepository eventoGiocoRepository;

    @GetMapping
    public List<EventoGioco> getAll() {
        return eventoGiocoRepository.findAll();
    }

    @GetMapping("/gioco/{giocoId}")
    public List<EventoGioco> getByGioco(@PathVariable String giocoId) {
        return eventoGiocoRepository.findByGiocoId(giocoId);
    }

    @GetMapping("/locale/{localeId}")
    public List<EventoGioco> getByLocale(@PathVariable String localeId) {
        return eventoGiocoRepository.findByLocaleId(localeId);
    }

    @PostMapping
    public ResponseEntity<EventoGioco> ricevi(@RequestBody JsonNode payload) {
        String giocoId = payload.has("giocoId") ? payload.get("giocoId").asText() : "unknown";
        String localeId = payload.has("localeId") ? payload.get("localeId").asText() : "unknown";
        String tipo = payload.has("tipo") ? payload.get("tipo").asText() : "unknown";

        EventoGioco evento = new EventoGioco(giocoId, localeId, tipo, payload.toString());
        return ResponseEntity.ok(eventoGiocoRepository.save(evento));
    }
}
