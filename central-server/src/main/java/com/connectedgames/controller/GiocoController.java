package com.connectedgames.controller;

import com.connectedgames.model.Gioco;
import com.connectedgames.service.GiocoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/giochi")
@RequiredArgsConstructor
public class GiocoController {

    private final GiocoService giocoService;

    @GetMapping
    public List<Gioco> getAll() {
        return giocoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Gioco> getById(@PathVariable Long id) {
        return ResponseEntity.ok(giocoService.findById(id));
    }

    @GetMapping("/locale/{localeId}")
    public List<Gioco> getByLocale(@PathVariable Long localeId) {
        return giocoService.findByLocale(localeId);
    }

    @PostMapping
    public ResponseEntity<Gioco> create(@RequestBody Gioco gioco) {
        return ResponseEntity.ok(giocoService.save(gioco));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        giocoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}