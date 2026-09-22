package com.playyourlist.musicas.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "musicas")
public class Musica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Título é obrigatório")
    @Column(nullable = false, length = 150)
    private String titulo;

    @NotBlank(message = "Artista é obrigatório")
    @Column(nullable = false, length = 150)
    private String artista;

    @Size(max = 150, message = "Álbum deve ter no máximo 150 caracteres")
    @Column(length = 150)
    private String album;

    @Positive(message = "Duração deve ser maior que zero")
    @Column(nullable = false)
    private Integer duracao;

    @Size(max = 50, message = "Gênero deve ter no máximo 50 caracteres")
    @Column(length = 50)
    private String genero;

    public Musica() {
    }

    public Musica(Long id, String titulo, String artista, String album, Integer duracao, String genero) {
        this.id = id;
        this.titulo = titulo;
        this.artista = artista;
        this.album = album;
        this.duracao = duracao;
        this.genero = genero;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public Integer getDuracao() {
        return duracao;
    }

    public void setDuracao(Integer duracao) {
        this.duracao = duracao;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
}
