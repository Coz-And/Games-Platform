package com.connectedgames.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "eventi_sensori")
public class EventoSensore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String giocoId;
    private String localeId;
    private String tipo;
    private String payload;
    private LocalDateTime ricevuto;
    private boolean sincronizzato;

    public EventoSensore() {}

    public EventoSensore(String giocoId, String localeId,
                         String tipo, String payload) {
        this.giocoId = giocoId;
        this.localeId = localeId;
        this.tipo = tipo;
        this.payload = payload;
        this.ricevuto = LocalDateTime.now();
        this.sincronizzato = false;
    }

    public Long getId() { return id; }
    public String getGiocoId() { return giocoId; }
    public String getLocaleId() { return localeId; }
    public String getTipo() { return tipo; }
    public String getPayload() { return payload; }
    public LocalDateTime getRicevuto() { return ricevuto; }
    public boolean isSincronizzato() { return sincronizzato; }
    public void setSincronizzato(boolean sincronizzato) {
        this.sincronizzato = sincronizzato;
    }
}