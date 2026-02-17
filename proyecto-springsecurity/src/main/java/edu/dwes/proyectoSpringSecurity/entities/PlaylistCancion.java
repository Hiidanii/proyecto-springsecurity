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
@Table(name = "playlists_canciones")
public class PlaylistCancion {
    @EmbeddedId
    private PlaylistCancionId id = new PlaylistCancionId();

    @ManyToOne
    @MapsId("playlistId")
    @JoinColumn(name = "playlist_id", nullable = false)
    private Playlist playlist;

    @ManyToOne
    @MapsId("cancionId")
    @JoinColumn(name = "cancion_id", nullable = false)
    private Cancion cancion;

    @NotNull(message = "La posición es obligatoria")
    @Min(value = 1, message = "La posición debe ser mayor o igual a 1")
    private Integer posicion;

    public PlaylistCancion() {
    }

    public PlaylistCancion(Playlist playlist, Cancion cancion, Integer posicion) {
        this.playlist = playlist;
        this.cancion = cancion;
        this.posicion = posicion;
        this.id = new PlaylistCancionId(playlist.getId(), cancion.getId());
    }

    public PlaylistCancionId getId() {
        return id;
    }

    public void setId(PlaylistCancionId id) {
        this.id = id;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    public Cancion getCancion() {
        return cancion;
    }

    public void setCancion(Cancion cancion) {
        this.cancion = cancion;
    }

    public Integer getPosicion() {
        return posicion;
    }

    public void setPosicion(Integer posicion) {
        this.posicion = posicion;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.id);
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
        final PlaylistCancion other = (PlaylistCancion) obj;
        return Objects.equals(this.id, other.id);
    }
}
