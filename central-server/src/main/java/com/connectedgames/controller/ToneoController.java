package com.connectedgames.controller;

import com.connectedgames.model.Torneo;
import com.connectedgames.service.TorneoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tornei")
@RequiredArgsConstructor
public class TorneoController {

    private final TorneoService torneoService;

    @GetMapping
    public List<Torneo> getAll() {
        return torneoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Torneo> getById(@PathVariable Long id) {
        return ResponseEntity.ok(torneoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Torneo> create(@RequestBody Torneo torneo) {
        return ResponseEntity.ok(torneoService.save(torneo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Torneo> update(@PathVariable Long id, @RequestBody Torneo torneo) {
        return ResponseEntity.ok(torneoService.update(id, torneo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        torneoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}