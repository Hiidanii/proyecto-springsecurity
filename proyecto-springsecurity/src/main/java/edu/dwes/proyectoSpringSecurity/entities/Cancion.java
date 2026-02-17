/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.proyectoSpringSecurity.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.*;

/**
 *
 * @author dani
 */
@Entity
@Table(name = "canciones")
public class Cancion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título es obligatorio")
    @Size(max = 50, message = "El título no puede tener más de 50 caracteres")
    private String titulo;

    @NotBlank(message = "El artista es obligatorio")
    @Size(max = 20, message = "El artista no puede tener más de 20 caracteres")
    private String artista;

    @NotNull(message = "La duración es obligatoria")
    @DecimalMin(value = "0.1", message = "La duracion debe ser mayor qe 0")
    private Double duracion;

    @NotBlank(message = "El género es obligatorio")
    @Size(max = 20, message = "El género no puede tener más de 20 caracteres")
    private String genero;

    @OneToMany(mappedBy = "cancion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlaylistCancion> playlistCancion = new ArrayList<>();

    public Cancion() {
    }

    public Cancion(Long id, String titulo, String artista, Double duracion, String genero) {
        this.id = id;
        this.titulo = titulo;
        this.artista = artista;
        this.duracion = duracion;
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

    public Double getDuracion() {
        return duracion;
    }

    public void setDuracion(Double duracion) {
        this.duracion = duracion;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public List<PlaylistCancion> getPlaylistCancion() {
        return playlistCancion;
    }

    public void setPlaylistCancion(List<PlaylistCancion> playlistCancion) {
        this.playlistCancion = playlistCancion;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cancion other = (Cancion) obj;
        return Objects.equals(this.id, other.id);
    }

    public void addPlaylistCancion(PlaylistCancion playlistCancion) {
        this.playlistCancion.add(playlistCancion);
        playlistCancion.setCancion(this);
    }

    public void removePlaylistCancion(PlaylistCancion playlistCancion) {
        this.playlistCancion.remove(playlistCancion);
        playlistCancion.setCancion(null);
    }
}
