/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.proyectoSpringSecurity.services;

import edu.dwes.proyectoSpringSecurity.entities.Cancion;
import edu.dwes.proyectoSpringSecurity.entities.PlaylistCancion;
import edu.dwes.proyectoSpringSecurity.repositories.CancionRepository;
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
public class CancionService {
    @Autowired
    private CancionRepository cancionRepository;

    // Listar todas las canciones
    public List<Cancion> findAll() {
        return cancionRepository.findAll();
    }

    // Buscar una canción por su ID
    public Cancion findById(Long id) {
        return cancionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Canción con ID " + id + " no encontrada"));
    }

    // Agregar / Actualizar una nueva canciónç
    @Transactional
    public Cancion save(Cancion cancion) {
        return cancionRepository.save(cancion);
    }

    @Transactional
    public void deleteById(Long id) {
        Cancion cancion = cancionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Canción con ID " + id + " no encontrada"));

        for (PlaylistCancion pc : new ArrayList<>(cancion.getPlaylistCancion())) {
            pc.getPlaylist().getPlaylistCancion().remove(pc);
            pc.setPlaylist(null);
            pc.setCancion(null);
        }
        cancion.getPlaylistCancion().clear();

        cancionRepository.delete(cancion);
    }
}
