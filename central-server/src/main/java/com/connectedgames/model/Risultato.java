package com.connectedgames.model;

import jakarta.persistence.*;

@Entity
@Table(name = "risultati")
public class Risultato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "partita_id", nullable = false)
    private Partita partita;

    @ManyToOne
    @JoinColumn(name = "vincitore_id")
    private Utente vincitore;

    private String punteggioJSON;
    private String dettagliJSON;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Partita getPartita() { return partita; }
    public void setPartita(Partita partita) { this.partita = partita; }
    public Utente getVincitore() { return vincitore; }
    public void setVincitore(Utente vincitore) { this.vincitore = vincitore; }
    public String getPunteggioJSON() { return punteggioJSON; }
    public void setPunteggioJSON(String punteggioJSON) { this.punteggioJSON = punteggioJSON; }
    public String getDettagliJSON() { return dettagliJSON; }
    public void setDettagliJSON(String dettagliJSON) { this.dettagliJSON = dettagliJSON; }
}