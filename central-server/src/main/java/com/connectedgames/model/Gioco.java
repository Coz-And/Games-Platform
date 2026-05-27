package com.connectedgames.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "giochi")
public class Gioco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoGioco tipo;

    @Column(nullable = false)
    private String identificatore;

    @ManyToOne
    @JoinColumn(name = "locale_id", nullable = false)
    private Locale locale;

    @OneToMany(mappedBy = "gioco", cascade = CascadeType.ALL)
    private List<Partita> partite;

    public enum TipoGioco {
        CALCIOBALILLA,
        FRECCETTE,
        BOCCE,
        MONOPOLI
    }
}