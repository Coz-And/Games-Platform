package com.connectedgames.controller;

import com.connectedgames.model.Locale;
import com.connectedgames.service.LocaleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/locali")
public class LocaleController {

    private final LocaleService localeService;

    public LocaleController(LocaleService localeService) {
        this.localeService = localeService;
    }

    @GetMapping
    public List<Locale> getAll() {
        return localeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Locale> getById(@PathVariable Long id) {
        return ResponseEntity.ok(localeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Locale> create(@RequestBody Locale locale) {
        return ResponseEntity.ok(localeService.save(locale));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Locale> update(@PathVariable Long id, @RequestBody Locale locale) {
        return ResponseEntity.ok(localeService.update(id, locale));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        localeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
