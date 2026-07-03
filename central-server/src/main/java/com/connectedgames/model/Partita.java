package com.connectedgames.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "partite")
public class Partita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "gioco_id", nullable = false)
    private Gioco gioco;

    @Column(nullable = false)
    private LocalDateTime iniziata;

    private LocalDateTime terminata;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatoPartita stato;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPartita tipo;

    @ManyToMany
    @JoinTable(
            name = "partita_giocatori",
            joinColumns = @JoinColumn(name = "partita_id"),
            inverseJoinColumns = @JoinColumn(name = "utente_id")
    )
    private List<Utente> giocatori;

    @OneToOne(mappedBy = "partita", cascade = CascadeType.ALL)
    private Risultato risultato;

    @ManyToOne
    @JoinColumn(name = "torneo_id")
    private Torneo torneo;

    public enum StatoPartita { IN_CORSO, TERMINATA, ANNULLATA }
    public enum TipoPartita { INDIVIDUALE, A_SQUADRE }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Gioco getGioco() { return gioco; }
    public void setGioco(Gioco gioco) { this.gioco = gioco; }
    public LocalDateTime getIniziata() { return iniziata; }
    public void setIniziata(LocalDateTime iniziata) { this.iniziata = iniziata; }
    public LocalDateTime getTerminata() { return terminata; }
    public void setTerminata(LocalDateTime terminata) { this.terminata = terminata; }
    public StatoPartita getStato() { return stato; }
    public void setStato(StatoPartita stato) { this.stato = stato; }
    public TipoPartita getTipo() { return tipo; }
    public void setTipo(TipoPartita tipo) { this.tipo = tipo; }
    public List<Utente> getGiocatori() { return giocatori; }
    public void setGiocatori(List<Utente> giocatori) { this.giocatori = giocatori; }
    public Risultato getRisultato() { return risultato; }
    public void setRisultato(Risultato risultato) { this.risultato = risultato; }
    public Torneo getTorneo() { return torneo; }
    public void setTorneo(Torneo torneo) { this.torneo = torneo; }
}