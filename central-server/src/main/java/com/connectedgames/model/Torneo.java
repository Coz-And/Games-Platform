package com.connectedgames.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tornei")
public class Torneo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gioco.TipoGioco tipoGioco;

    @Column(nullable = false)
    private LocalDateTime inizioTorneo;

    private LocalDateTime fineTorneo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatoTorneo stato;

    @ManyToMany
    @JoinTable(
            name = "torneo_locali",
            joinColumns = @JoinColumn(name = "torneo_id"),
            inverseJoinColumns = @JoinColumn(name = "locale_id")
    )
    private List<Locale> locali;

    @OneToMany(mappedBy = "torneo")
    private List<Partita> partite;

    public enum StatoTorneo { PROGRAMMATO, IN_CORSO, TERMINATO }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Gioco.TipoGioco getTipoGioco() { return tipoGioco; }
    public void setTipoGioco(Gioco.TipoGioco tipoGioco) { this.tipoGioco = tipoGioco; }
    public LocalDateTime getInizioTorneo() { return inizioTorneo; }
    public void setInizioTorneo(LocalDateTime inizioTorneo) { this.inizioTorneo = inizioTorneo; }
    public LocalDateTime getFineTorneo() { return fineTorneo; }
    public void setFineTorneo(LocalDateTime fineTorneo) { this.fineTorneo = fineTorneo; }
    public StatoTorneo getStato() { return stato; }
    public void setStato(StatoTorneo stato) { this.stato = stato; }
    public List<Locale> getLocali() { return locali; }
    public void setLocali(List<Locale> locali) { this.locali = locali; }
    public List<Partita> getPartite() { return partite; }
    public void setPartite(List<Partita> partite) { this.partite = partite; }
}