package com.connectedgames.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Evento sensore ricevuto dall'edge-server (via sincronizzazione REST) e
 * archiviato sul server centrale. Il payload originale (JSON grezzo prodotto
 * dal sensor-simulator e inoltrato via MQTT) viene conservato integralmente
 * per poter essere consultato o rielaborato in un secondo momento.
 */
@Entity
@Table(name = "eventi")
public class EventoGioco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String giocoId;
    private String localeId;
    private String tipo;

    @Column(length = 2000)
    private String payload;

    @Column(nullable = false)
    private LocalDateTime ricevuto;

    public EventoGioco() {}

    public EventoGioco(String giocoId, String localeId, String tipo, String payload) {
        this.giocoId = giocoId;
        this.localeId = localeId;
        this.tipo = tipo;
        this.payload = payload;
        this.ricevuto = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getGiocoId() { return giocoId; }
    public void setGiocoId(String giocoId) { this.giocoId = giocoId; }
    public String getLocaleId() { return localeId; }
    public void setLocaleId(String localeId) { this.localeId = localeId; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getPayload() { return payload; }
    public void setPayload(String payload) { this.payload = payload; }
    public LocalDateTime getRicevuto() { return ricevuto; }
    public void setRicevuto(LocalDateTime ricevuto) { this.ricevuto = ricevuto; }
}
