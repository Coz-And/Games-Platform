package com.connectedgames.service;

import com.connectedgames.model.Gioco;
import com.connectedgames.model.Torneo;
import com.connectedgames.repository.TorneoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UtenteControllerTest {

    @Mock
    private TorneoRepository torneoRepository;

    @InjectMocks
    private TorneoService torneoService;

    private Torneo torneo;

    @BeforeEach
    void setUp() {
        torneo = new Torneo();
        torneo.setId(1L);
        torneo.setNome("Torneo Calciobalilla 2026");
        torneo.setTipoGioco(Gioco.TipoGioco.CALCIOBALILLA);
        torneo.setInizioTorneo(LocalDateTime.now());
        torneo.setStato(Torneo.StatoTorneo.PROGRAMMATO);
    }

    @Test
    void findAll_ritornaListaTornei() {
        when(torneoRepository.findAll()).thenReturn(List.of(torneo));
        List<Torneo> result = torneoService.findAll();
        assertEquals(1, result.size());
    }

    @Test
    void findById_torneoEsiste_ritornaTorneo() {
        when(torneoRepository.findById(1L)).thenReturn(Optional.of(torneo));
        Torneo result = torneoService.findById(1L);
        assertEquals("Torneo Calciobalilla 2026", result.getNome());
    }

    @Test
    void findById_torneoNonEsiste_lanceEccezione() {
        when(torneoRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> torneoService.findById(99L));
    }

    @Test
    void save_impostaStatoProgrammato() {
        when(torneoRepository.save(any())).thenReturn(torneo);
        Torneo result = torneoService.save(torneo);
        assertEquals(Torneo.StatoTorneo.PROGRAMMATO, result.getStato());
    }
}