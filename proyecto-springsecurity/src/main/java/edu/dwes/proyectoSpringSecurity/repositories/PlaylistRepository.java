/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.proyectoSpringSecurity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.dwes.proyectoSpringSecurity.entities.Playlist;

/**
 *
 * @author daniel
 */

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    
}