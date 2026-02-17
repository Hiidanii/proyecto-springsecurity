/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package edu.dwes.proyectoSpringSecurity.controllers;

import edu.dwes.proyectoSpringSecurity.entities.Cancion;
import edu.dwes.proyectoSpringSecurity.services.CancionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 *
 * @author dani
 */
@Controller
@RequestMapping("/canciones")
public class CancionController {
	@Autowired
	private CancionService cancionService;

	// Listar todas las canciones
	@GetMapping("")
	public String listarCanciones(Model model) {
		List<Cancion> canciones = cancionService.findAll();
		model.addAttribute("canciones", canciones);
		return "canciones/list";
	}

    // Buscar una canción por su ID
    @GetMapping("/{id}") public String mostrarDetallesCancion(@PathVariable Long id, Model model) {
        Cancion cancion = cancionService.findById(id);
        if (cancion == null) {
            return "redirect:/canciones";
        }
        model.addAttribute("cancion", cancion);
        return "canciones/details";
    }

	// Mostrar formulario para crear una nueva canción
	@GetMapping("/nueva")
	public String mostrarFormularioNuevaCancion(Model model) {
		model.addAttribute("cancion", new Cancion());
		return "canciones/form";
	}

	// Guardar una nueva canción
	@PostMapping("/guardar")
	public String guardarCancion(@Valid @ModelAttribute("cancion") Cancion cancion, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "canciones/form";
		}
		cancionService.save(cancion);
		return "redirect:/canciones";
	}

	// Mostrar formulario para editar una canción existente
	@GetMapping("/editar/{id}")
	public String mostrarFormularioEditarCancion(@PathVariable Long id, Model model) {
		Cancion cancion = cancionService.findById(id);
		if (cancion == null) {
			return "redirect:/canciones";
		}
		model.addAttribute("cancion", cancion);
		return "canciones/form";
	}

	// Eliminar una canción
	@PostMapping("/eliminar/{id}")
	public String eliminarCancion(@PathVariable Long id, RedirectAttributes redirectAttrs) {
		try {
			cancionService.deleteById(id);
		} catch (RuntimeException e) {
			redirectAttrs.addFlashAttribute("error", e.getMessage());
		}
		return "redirect:/canciones";
	}
}
