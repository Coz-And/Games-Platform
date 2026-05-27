package com.connectedgames.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "locali")
public class Locale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @NotBlank
    @Column(nullable = false)
    private String indirizzo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoLocale tipo;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Utente amministratore;

    @OneToMany(mappedBy = "locale", cascade = CascadeType.ALL)
    private List<Gioco> giochi;

    public enum TipoLocale {
        PUBBLICO,
        PRIVATO
    }
}