package com.connectedgames.service;

import com.connectedgames.model.Torneo;
import com.connectedgames.repository.TorneoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TorneoService {

    private final TorneoRepository torneoRepository;

    public TorneoService(TorneoRepository torneoRepository) {
        this.torneoRepository = torneoRepository;
    }

    public List<Torneo> findAll() {
        return torneoRepository.findAll();
    }

    public Torneo findById(Long id) {
        return torneoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Torneo non trovato con id: " + id));
    }

    public List<Torneo> findByStato(Torneo.StatoTorneo stato) {
        return torneoRepository.findByStato(stato);
    }

    public Torneo save(Torneo torneo) {
        torneo.setStato(Torneo.StatoTorneo.PROGRAMMATO);
        return torneoRepository.save(torneo);
    }

    public Torneo update(Long id, Torneo torneo) {
        Torneo esistente = findById(id);
        esistente.setNome(torneo.getNome());
        esistente.setStato(torneo.getStato());
        esistente.setFineTorneo(torneo.getFineTorneo());
        return torneoRepository.save(esistente);
    }

    public void delete(Long id) {
        torneoRepository.deleteById(id);
    }
}