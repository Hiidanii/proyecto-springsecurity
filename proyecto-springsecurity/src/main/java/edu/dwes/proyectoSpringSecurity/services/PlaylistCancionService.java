/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.proyectoSpringSecurity.services;

import edu.dwes.proyectoSpringSecurity.entities.Cancion;
import edu.dwes.proyectoSpringSecurity.entities.Playlist;
import edu.dwes.proyectoSpringSecurity.entities.PlaylistCancion;
import edu.dwes.proyectoSpringSecurity.entities.PlaylistCancionId;
import edu.dwes.proyectoSpringSecurity.repositories.CancionRepository;
import edu.dwes.proyectoSpringSecurity.repositories.PlaylistCancionRepository;
import edu.dwes.proyectoSpringSecurity.repositories.PlaylistRepository;
import jakarta.transaction.Transactional;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author dani
 */
@Service
public class PlaylistCancionService {
    @Autowired
    private PlaylistCancionRepository playlistCancionRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private CancionRepository cancionRepository;

    // Listar todas las relaciones Playlist-Cancion
    public List<PlaylistCancion> findAll() {
        return playlistCancionRepository.findAll();
    }

    // Buscar una relación Playlist-Cancion por su ID compuesto
    public PlaylistCancion findById(PlaylistCancionId id) {
        return playlistCancionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Relacion con ID " + id + " no encontrada"));
    }

    // Agregar / Actualizar una nueva relación Playlist-Cancion
    @Transactional
    public PlaylistCancion save(Long playlistId, Long cancionId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist no encontrada"));
        Cancion cancion = cancionRepository.findById(cancionId)
                .orElseThrow(() -> new RuntimeException("Canción no encontrada"));

        PlaylistCancionId pcId = new PlaylistCancionId(playlistId, cancionId);
        if (playlistCancionRepository.existsById(pcId)) {
            throw new RuntimeException("Esa canción ya está en la playlist");
        }

        // Posición autoincremental: cuenta cuántas canciones tiene ya la playlist
        int posicion = playlistCancionRepository.findByIdPlaylistId(playlistId).size() + 1;

        PlaylistCancion pc = new PlaylistCancion(playlist, cancion, posicion);

        return playlistCancionRepository.save(pc);
    }

    // Eliminar una relación Playlist-Cancion por su ID compuesto
    @Transactional
    public void deleteById(PlaylistCancionId id) {
        PlaylistCancion pc = playlistCancionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Relación no encontrada"));

        pc.getPlaylist().getPlaylistCancion().remove(pc);
        pc.getCancion().getPlaylistCancion().remove(pc);
        pc.setPlaylist(null);
        pc.setCancion(null);

        playlistCancionRepository.delete(pc);
    }
}
