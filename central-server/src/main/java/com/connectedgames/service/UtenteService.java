package com.connectedgames.service;

import com.connectedgames.model.Utente;
import com.connectedgames.repository.UtenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UtenteService {

    private final UtenteRepository utenteRepository;

    public List<Utente> findAll() {
        return utenteRepository.findAll();
    }

    public Utente findById(Long id) {
        return utenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utente non trovato con id: " + id));
    }

    public Utente save(Utente utente) {
        if (utenteRepository.existsByEmail(utente.getEmail())) {
            throw new RuntimeException("Email già registrata: " + utente.getEmail());
        }
        return utenteRepository.save(utente);
    }

    public Utente update(Long id, Utente utente) {
        Utente esistente = findById(id);
        esistente.setNome(utente.getNome());
        esistente.setCognome(utente.getCognome());
        esistente.setRuolo(utente.getRuolo());
        return utenteRepository.save(esistente);
    }

    public void delete(Long id) {
        utenteRepository.deleteById(id);
    }
}