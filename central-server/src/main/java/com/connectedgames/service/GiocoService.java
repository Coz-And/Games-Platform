package com.connectedgames.service;

import com.connectedgames.model.Gioco;
import com.connectedgames.repository.GiocoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GiocoService {

    private final GiocoRepository giocoRepository;

    public List<Gioco> findAll() {
        return giocoRepository.findAll();
    }

    public Gioco findById(Long id) {
        return giocoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gioco non trovato con id: " + id));
    }

    public List<Gioco> findByLocale(Long localeId) {
        return giocoRepository.findByLocaleId(localeId);
    }

    public Gioco save(Gioco gioco) {
        return giocoRepository.save(gioco);
    }

    public void delete(Long id) {
        giocoRepository.deleteById(id);
    }
}