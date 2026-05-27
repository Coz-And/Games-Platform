package com.connectedgames.controller;

import com.connectedgames.model.Utente;
import com.connectedgames.service.UtenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/utenti")
@RequiredArgsConstructor
public class UtenteController {

    private final UtenteService utenteService;

    @GetMapping
    public List<Utente> getAll() {
        return utenteService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Utente> getById(@PathVariable Long id) {
        return ResponseEntity.ok(utenteService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Utente> create(@RequestBody Utente utente) {
        return ResponseEntity.ok(utenteService.save(utente));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Utente> update(@PathVariable Long id, @RequestBody Utente utente) {
        return ResponseEntity.ok(utenteService.update(id, utente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        utenteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}