package com.connectedgames.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

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

    public enum TipoGioco { CALCIOBALILLA, FRECCETTE, BOCCE, MONOPOLI }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public TipoGioco getTipo() { return tipo; }
    public void setTipo(TipoGioco tipo) { this.tipo = tipo; }
    public String getIdentificatore() { return identificatore; }
    public void setIdentificatore(String identificatore) { this.identificatore = identificatore; }
    public Locale getLocale() { return locale; }
    public void setLocale(Locale locale) { this.locale = locale; }
    public List<Partita> getPartite() { return partite; }
    public void setPartite(List<Partita> partite) { this.partite = partite; }
}