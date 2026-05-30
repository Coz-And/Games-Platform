package com.connectedgames.controller;

import com.connectedgames.model.Partita;
import com.connectedgames.service.PartitaService;
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

@WebMvcTest(PartitaController.class)
class PartitaControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private PartitaService partitaService;
    @Autowired private ObjectMapper objectMapper;

    private Partita partita;

    @BeforeEach
    void setUp() {
        partita = new Partita();
        partita.setId(1L);
        partita.setTipo(Partita.TipoPartita.INDIVIDUALE);
        partita.setStato(Partita.StatoPartita.IN_CORSO);
    }

    @Test
    @WithMockUser
    void getAll_ritornaListaPartite() throws Exception {
        when(partitaService.findAll()).thenReturn(List.of(partita));
        mockMvc.perform(get("/api/partite"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].stato").value("IN_CORSO"));
    }

    @Test
    @WithMockUser
    void termina_partita_ritornaTerminata() throws Exception {
        partita.setStato(Partita.StatoPartita.TERMINATA);
        when(partitaService.terminaPartita(1L)).thenReturn(partita);
        mockMvc.perform(put("/api/partite/1/termina"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stato").value("TERMINATA"));
    }
}