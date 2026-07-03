package com.connectedgames.service;

import com.connectedgames.model.Utente;
import com.connectedgames.repository.UtenteRepository;
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
class UtenteServiceTest {

    @Mock
    private UtenteRepository utenteRepository;

    @InjectMocks
    private UtenteService utenteService;

    private Utente utente;

    @BeforeEach
    void setUp() {
        utente = new Utente();
        utente.setId(1L);
        utente.setNome("Mario");
        utente.setCognome("Rossi");
        utente.setEmail("mario.rossi@test.it");
        utente.setPasswordHash("hash123");
        utente.setRuolo(Utente.RuoloUtente.GIOCATORE);
    }

    @Test
    void findAll_ritornaListaUtenti() {
        when(utenteRepository.findAll()).thenReturn(List.of(utente));
        List<Utente> result = utenteService.findAll();
        assertEquals(1, result.size());
        verify(utenteRepository).findAll();
    }

    @Test
    void findByRuolo_ritornaSoloUtentiConQuelRuolo() {
        when(utenteRepository.findByRuolo(Utente.RuoloUtente.GIOCATORE)).thenReturn(List.of(utente));
        List<Utente> result = utenteService.findByRuolo(Utente.RuoloUtente.GIOCATORE);
        assertEquals(1, result.size());
        assertEquals(Utente.RuoloUtente.GIOCATORE, result.get(0).getRuolo());
        verify(utenteRepository).findByRuolo(Utente.RuoloUtente.GIOCATORE);
    }

    @Test
    void cambiaRuolo_aggiornaIlRuoloDellUtente() {
        when(utenteRepository.findById(1L)).thenReturn(Optional.of(utente));
        when(utenteRepository.save(utente)).thenReturn(utente);
        Utente result = utenteService.cambiaRuolo(1L, Utente.RuoloUtente.ADMIN_LOCALE);
        assertEquals(Utente.RuoloUtente.ADMIN_LOCALE, result.getRuolo());
        verify(utenteRepository).save(utente);
    }

    @Test
    void findById_utenteEsiste_ritornaUtente() {
        when(utenteRepository.findById(1L)).thenReturn(Optional.of(utente));
        Utente result = utenteService.findById(1L);
        assertEquals("Mario", result.getNome());
    }

    @Test
    void findById_utenteNonEsiste_lanceEccezione() {
        when(utenteRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> utenteService.findById(99L));
    }

    @Test
    void save_emailNuova_salvaUtente() {
        when(utenteRepository.existsByEmail(utente.getEmail())).thenReturn(false);
        when(utenteRepository.save(utente)).thenReturn(utente);
        Utente result = utenteService.save(utente);
        assertEquals("mario.rossi@test.it", result.getEmail());
    }

    @Test
    void save_emailGiaEsistente_lanceEccezione() {
        when(utenteRepository.existsByEmail(utente.getEmail())).thenReturn(true);
        assertThrows(RuntimeException.class, () -> utenteService.save(utente));
    }

    @Test
    void delete_chiamaRepository() {
        utenteService.delete(1L);
        verify(utenteRepository).deleteById(1L);
    }
}