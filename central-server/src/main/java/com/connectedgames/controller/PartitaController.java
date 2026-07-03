package com.connectedgames.controller;

import com.connectedgames.model.Partita;
import com.connectedgames.service.PartitaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/partite")
public class PartitaController {

    private final PartitaService partitaService;

    public PartitaController(PartitaService partitaService) {
        this.partitaService = partitaService;
    }

    @GetMapping
    public List<Partita> getAll() {
        return partitaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Partita> getById(@PathVariable Long id) {
        return ResponseEntity.ok(partitaService.findById(id));
    }

    @GetMapping("/giocatore/{utenteId}")
    public List<Partita> getByGiocatore(@PathVariable Long utenteId) {
        return partitaService.findByGiocatore(utenteId);
    }

    @PostMapping
    public ResponseEntity<Partita> inizia(@RequestBody Partita partita) {
        return ResponseEntity.ok(partitaService.iniziaPartita(partita));
    }

    @PutMapping("/{id}/termina")
    public ResponseEntity<Partita> termina(@PathVariable Long id) {
        return ResponseEntity.ok(partitaService.terminaPartita(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        partitaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
