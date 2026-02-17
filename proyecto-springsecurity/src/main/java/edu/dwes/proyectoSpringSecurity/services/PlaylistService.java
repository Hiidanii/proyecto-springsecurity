/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.proyectoSpringSecurity.services;

import edu.dwes.proyectoSpringSecurity.entities.Playlist;
import edu.dwes.proyectoSpringSecurity.entities.PlaylistCancion;
import edu.dwes.proyectoSpringSecurity.repositories.PlaylistRepository;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author dani
 */
@Service
public class PlaylistService {
    @Autowired
    private PlaylistRepository playlistRepository;

    // Listar todas las playlists
    public List<Playlist> findAll() {
        return playlistRepository.findAll();
    }

    // Buscar una playlist por su ID
    public Playlist findById(Long id) {
        return playlistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Playlist con ID " + id + " no encontrada"));
    }

    // Agregar / Actualizar una nueva playlist
    @Transactional
    public Playlist save(Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    // Eliminar una playlist por su ID
    @Transactional
    public void deleteById(Long id) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Playlist con ID " + id + " no encontrada"));

        for (PlaylistCancion pc : new ArrayList<>(playlist.getPlaylistCancion())) {
            pc.getCancion().getPlaylistCancion().remove(pc);
            pc.setCancion(null);
            pc.setPlaylist(null);
        }
        playlist.getPlaylistCancion().clear();

        playlistRepository.delete(playlist);
    }
}
