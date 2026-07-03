package com.connectedgames.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

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

    public enum TipoLocale { PUBBLICO, PRIVATO }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getIndirizzo() { return indirizzo; }
    public void setIndirizzo(String indirizzo) { this.indirizzo = indirizzo; }
    public TipoLocale getTipo() { return tipo; }
    public void setTipo(TipoLocale tipo) { this.tipo = tipo; }
    public Utente getAmministratore() { return amministratore; }
    public void setAmministratore(Utente amministratore) { this.amministratore = amministratore; }
    public List<Gioco> getGiochi() { return giochi; }
    public void setGiochi(List<Gioco> giochi) { this.giochi = giochi; }
}