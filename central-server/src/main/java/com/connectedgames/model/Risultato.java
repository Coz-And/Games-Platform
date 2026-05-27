package com.connectedgames.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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
}