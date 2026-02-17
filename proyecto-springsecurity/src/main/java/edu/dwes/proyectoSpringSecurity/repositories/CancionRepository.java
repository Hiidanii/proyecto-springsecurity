/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.proyectoSpringSecurity.repositories;

import edu.dwes.proyectoSpringSecurity.entities.Cancion;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author dani
 */
public interface CancionRepository extends JpaRepository<Cancion, Long> {
    
}
