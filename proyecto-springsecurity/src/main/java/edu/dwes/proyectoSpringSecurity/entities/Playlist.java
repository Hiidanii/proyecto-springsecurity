/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.proyectoSpringSecurity.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.*;

/**
 *
 * @author dani
 */
@Entity
@Table(name = "playlists")
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 200, message = "La descripción no puede tener más de 200 caracteres")
    private String descripcion;

    @NotNull(message = "La fecha de creación es obligatoria")
    @PastOrPresent(message = "La fecha de creación debe ser en el pasado")
    private LocalDate fechaCreacion;

    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlaylistCancion> playlistCancion = new ArrayList<>();

    public Playlist() {
    }

    public Playlist(Long id, String nombre, String descripcion, LocalDate fechaCreacion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public List<PlaylistCancion> getPlaylistCancion() {
        return playlistCancion;
    }

    public void setPlaylistCancion(List<PlaylistCancion> playlistCancion) {
        this.playlistCancion = playlistCancion;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.id);
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
        final Playlist other = (Playlist) obj;
        return Objects.equals(this.id, other.id);
    }

    public void addPlaylistCancion(PlaylistCancion playlistCancion) {
        this.playlistCancion.add(playlistCancion);
        playlistCancion.setPlaylist(this);
    }

    public void removePlaylistCancion(PlaylistCancion playlistCancion) {
        this.playlistCancion.remove(playlistCancion);
        playlistCancion.setPlaylist(null);
    }    
}
