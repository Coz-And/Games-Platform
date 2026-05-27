package com.connectedgames.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
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

    public enum StatoPartita {
        IN_CORSO,
        TERMINATA,
        ANNULLATA
    }

    public enum TipoPartita {
        INDIVIDUALE,
        A_SQUADRE
    }
}