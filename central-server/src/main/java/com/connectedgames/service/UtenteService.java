package com.connectedgames.service;

import com.connectedgames.model.Utente;
import com.connectedgames.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Utente> findAll() {
        return utenteRepository.findAll();
    }

    public Utente findById(Long id) {
        return utenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utente non trovato con id: " + id));
    }

    public Utente findByUsername(String username) {
        return utenteRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utente non trovato: " + username));
    }

    public Utente registra(String nome, String cognome, String email,
                           String username, String password, String ruolo) {
        if (utenteRepository.existsByEmail(email)) {
            throw new RuntimeException("Email già registrata");
        }
        if (utenteRepository.existsByUsername(username)) {
            throw new RuntimeException("Username già in uso");
        }
        Utente utente = new Utente();
        utente.setNome(nome);
        utente.setCognome(cognome);
        utente.setEmail(email);
        utente.setUsername(username);
        utente.setPasswordHash(passwordEncoder.encode(password));
        utente.setRuolo(Utente.RuoloUtente.valueOf(ruolo));
        return utenteRepository.save(utente);
    }

    public Utente save(Utente utente) {
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