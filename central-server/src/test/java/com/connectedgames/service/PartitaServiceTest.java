package com.connectedgames.service;

import com.connectedgames.model.Partita;
import com.connectedgames.repository.PartitaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PartitaServiceTest {

    @Mock
    private PartitaRepository partitaRepository;

    @InjectMocks
    private PartitaService partitaService;

    private Partita partita;

    @BeforeEach
    void setUp() {
        partita = new Partita();
        partita.setId(1L);
        partita.setTipo(Partita.TipoPartita.INDIVIDUALE);
        partita.setStato(Partita.StatoPartita.IN_CORSO);
    }

    @Test
    void findAll_ritornaListaPartite() {
        when(partitaRepository.findAll()).thenReturn(List.of(partita));
        List<Partita> result = partitaService.findAll();
        assertEquals(1, result.size());
    }

    @Test
    void findById_partitaEsiste_ritornaPartita() {
        when(partitaRepository.findById(1L)).thenReturn(Optional.of(partita));
        Partita result = partitaService.findById(1L);
        assertEquals(Partita.TipoPartita.INDIVIDUALE, result.getTipo());
    }

    @Test
    void findById_partitaNonEsiste_lanceEccezione() {
        when(partitaRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> partitaService.findById(99L));
    }

    @Test
    void iniziaPartita_impostaStatoEData() {
        when(partitaRepository.save(any())).thenReturn(partita);
        Partita result = partitaService.iniziaPartita(partita);
        assertEquals(Partita.StatoPartita.IN_CORSO, result.getStato());
        assertNotNull(result.getIniziata());
    }

    @Test
    void terminaPartita_impostaStatoTerminata() {
        when(partitaRepository.findById(1L)).thenReturn(Optional.of(partita));
        when(partitaRepository.save(any())).thenReturn(partita);
        Partita result = partitaService.terminaPartita(1L);
        assertEquals(Partita.StatoPartita.TERMINATA, result.getStato());
        assertNotNull(result.getTerminata());
    }
}