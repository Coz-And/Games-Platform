package com.connectedgames.service;

import com.connectedgames.model.Locale;
import com.connectedgames.repository.LocaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LocaleService {

    @Autowired
    private LocaleRepository localeRepository;

    public List<Locale> findAll() {
        return localeRepository.findAll();
    }

    public Locale findById(Long id) {
        return localeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Locale non trovato con id: " + id));
    }

    public Locale save(Locale locale) {
        return localeRepository.save(locale);
    }

    public Locale update(Long id, Locale locale) {
        Locale esistente = findById(id);
        esistente.setNome(locale.getNome());
        esistente.setIndirizzo(locale.getIndirizzo());
        esistente.setTipo(locale.getTipo());
        return localeRepository.save(esistente);
    }

    public void delete(Long id) {
        localeRepository.deleteById(id);
    }
}