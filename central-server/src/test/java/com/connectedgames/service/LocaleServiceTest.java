package com.connectedgames.service;

import com.connectedgames.model.Locale;
import com.connectedgames.repository.LocaleRepository;
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
class LocaleServiceTest {

    @Mock
    private LocaleRepository localeRepository;

    @InjectMocks
    private LocaleService localeService;

    private Locale locale;

    @BeforeEach
    void setUp() {
        locale = new Locale();
        locale.setId(1L);
        locale.setNome("Bar Sport");
        locale.setIndirizzo("Via Roma 1, Milano");
        locale.setTipo(Locale.TipoLocale.PUBBLICO);
    }

    @Test
    void findAll_ritornaListaLocali() {
        when(localeRepository.findAll()).thenReturn(List.of(locale));
        List<Locale> result = localeService.findAll();
        assertEquals(1, result.size());
        verify(localeRepository).findAll();
    }

    @Test
    void findById_localeEsiste_ritornaLocale() {
        when(localeRepository.findById(1L)).thenReturn(Optional.of(locale));
        Locale result = localeService.findById(1L);
        assertEquals("Bar Sport", result.getNome());
    }

    @Test
    void findById_localeNonEsiste_lanceEccezione() {
        when(localeRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> localeService.findById(99L));
    }

    @Test
    void save_salvaLocale() {
        when(localeRepository.save(locale)).thenReturn(locale);
        Locale result = localeService.save(locale);
        assertEquals("Bar Sport", result.getNome());
    }

    @Test
    void delete_chiamaRepository() {
        localeService.delete(1L);
        verify(localeRepository).deleteById(1L);
    }
}