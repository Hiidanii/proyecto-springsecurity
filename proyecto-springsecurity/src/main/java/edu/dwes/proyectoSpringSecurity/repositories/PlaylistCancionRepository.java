/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.proyectoSpringSecurity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.dwes.proyectoSpringSecurity.entities.PlaylistCancion;
import edu.dwes.proyectoSpringSecurity.entities.PlaylistCancionId;

import java.util.List;

/**
 *
 * @author daniel
 */
public interface PlaylistCancionRepository extends JpaRepository<PlaylistCancion, PlaylistCancionId> {
    List<PlaylistCancion> findByIdPlaylistId(Long playlistId);
}