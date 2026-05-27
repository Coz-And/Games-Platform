package com.connectedgames.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
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

    public enum StatoTorneo {
        PROGRAMMATO,
        IN_CORSO,
        TERMINATO
    }
}