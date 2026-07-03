package com.connectedgames.controller;

import com.connectedgames.model.Locale;
import com.connectedgames.service.LocaleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LocaleController.class)
class LocaleControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private LocaleService localeService;
    @Autowired private ObjectMapper objectMapper;

    private Locale locale;

    @BeforeEach
    void setUp() {
        locale = new Locale();
        locale.setId(1L);
        locale.setNome("Bar Sport");
        locale.setIndirizzo("Via Roma 1");
        locale.setTipo(Locale.TipoLocale.PUBBLICO);
    }

    @Test
    @WithMockUser
    void getAll_ritornaListaLocali() throws Exception {
        when(localeService.findAll()).thenReturn(List.of(locale));
        mockMvc.perform(get("/api/locali"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Bar Sport"));
    }

    @Test
    @WithMockUser
    void getById_localeEsiste_ritornaLocale() throws Exception {
        when(localeService.findById(1L)).thenReturn(locale);
        mockMvc.perform(get("/api/locali/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Bar Sport"));
    }

    @Test
    @WithMockUser
    void create_localeValido_ritornaLocale() throws Exception {
        when(localeService.save(any())).thenReturn(locale);
        mockMvc.perform(post("/api/locali")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(locale)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void delete_chiamaService() throws Exception {
        mockMvc.perform(delete("/api/locali/1"))
                .andExpect(status().isNoContent());
        verify(localeService).delete(1L);
    }
}