package com.connectedgames.controller;

import com.connectedgames.model.Utente;
import com.connectedgames.service.UtenteService;
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

@WebMvcTest(UtenteController.class)
class UtenteControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private UtenteService utenteService;
    @Autowired private ObjectMapper objectMapper;

    private Utente utente;

    @BeforeEach
    void setUp() {
        utente = new Utente();
        utente.setId(1L);
        utente.setNome("Mario");
        utente.setCognome("Rossi");
        utente.setEmail("mario.rossi@test.it");
        utente.setUsername("mario_r");
        utente.setPasswordHash("hash123");
        utente.setRuolo(Utente.RuoloUtente.GIOCATORE);
    }

    @Test
    @WithMockUser
    void getAll_ritornaListaUtenti() throws Exception {
        when(utenteService.findAll()).thenReturn(List.of(utente));
        mockMvc.perform(get("/api/utenti"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("mario_r"));
    }

    @Test
    @WithMockUser
    void getById_utenteEsiste_ritornaUtente() throws Exception {
        when(utenteService.findById(1L)).thenReturn(utente);
        mockMvc.perform(get("/api/utenti/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("mario.rossi@test.it"));
    }

    @Test
    @WithMockUser
    void create_utenteValido_ritornaUtente() throws Exception {
        when(utenteService.save(any())).thenReturn(utente);
        mockMvc.perform(post("/api/utenti")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(utente)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("mario_r"));
    }

    @Test
    @WithMockUser
    void delete_chiamaService() throws Exception {
        mockMvc.perform(delete("/api/utenti/1"))
                .andExpect(status().isNoContent());
        verify(utenteService).delete(1L);
    }
}
