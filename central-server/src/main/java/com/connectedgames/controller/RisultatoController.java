package com.connectedgames.controller;

import com.connectedgames.model.Risultato;
import com.connectedgames.repository.RisultatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/risultati")
public class RisultatoController {

    @Autowired
    private RisultatoRepository risultatoRepository;

    @GetMapping
    public List<Risultato> getAll() {
        return risultatoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Risultato> getById(@PathVariable Long id) {
        return risultatoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Risultato> create(@RequestBody Risultato risultato) {
        return ResponseEntity.ok(risultatoRepository.save(risultato));
    }
}