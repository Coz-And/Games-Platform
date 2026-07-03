package com.connectedgames.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "prenotazioni")
public class Prenotazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "locale_id", nullable = false)
    private Locale locale;

    @ManyToOne
    @JoinColumn(name = "gioco_id", nullable = false)
    private Gioco gioco;

    @Column(nullable = false)
    private LocalDateTime dataOra;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatoPrenotazione stato;

    @Column(nullable = false)
    private LocalDateTime creata;

    public enum StatoPrenotazione { CONFERMATA, ANNULLATA }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Utente getUtente() { return utente; }
    public void setUtente(Utente utente) { this.utente = utente; }
    public Locale getLocale() { return locale; }
    public void setLocale(Locale locale) { this.locale = locale; }
    public Gioco getGioco() { return gioco; }
    public void setGioco(Gioco gioco) { this.gioco = gioco; }
    public LocalDateTime getDataOra() { return dataOra; }
    public void setDataOra(LocalDateTime dataOra) { this.dataOra = dataOra; }
    public StatoPrenotazione getStato() { return stato; }
    public void setStato(StatoPrenotazione stato) { this.stato = stato; }
    public LocalDateTime getCreata() { return creata; }
    public void setCreata(LocalDateTime creata) { this.creata = creata; }
}
