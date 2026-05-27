package com.connectedgames.service;

import com.connectedgames.model.Gioco;
import com.connectedgames.repository.GiocoRepository;
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
class GiocoServiceTest {

    @Mock
    private GiocoRepository giocoRepository;

    @InjectMocks
    private GiocoService giocoService;

    private Gioco gioco;

    @BeforeEach
    void setUp() {
        gioco = new Gioco();
        gioco.setId(1L);
        gioco.setNome("Calciobalilla 1");
        gioco.setTipo(Gioco.TipoGioco.CALCIOBALILLA);
        gioco.setIdentificatore("CALC-001");
    }

    @Test
    void findAll_ritornaListaGiochi() {
        when(giocoRepository.findAll()).thenReturn(List.of(gioco));
        List<Gioco> result = giocoService.findAll();
        assertEquals(1, result.size());
    }

    @Test
    void findById_giocoEsiste_ritornaGioco() {
        when(giocoRepository.findById(1L)).thenReturn(Optional.of(gioco));
        Gioco result = giocoService.findById(1L);
        assertEquals("CALC-001", result.getIdentificatore());
    }

    @Test
    void findById_giocoNonEsiste_lanceEccezione() {
        when(giocoRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> giocoService.findById(99L));
    }

    @Test
    void findByLocale_ritornaGiochiDelLocale() {
        when(giocoRepository.findByLocaleId(1L)).thenReturn(List.of(gioco));
        List<Gioco> result = giocoService.findByLocale(1L);
        assertEquals(1, result.size());
    }

    @Test
    void delete_chiamaRepository() {
        giocoService.delete(1L);
        verify(giocoRepository).deleteById(1L);
    }
}