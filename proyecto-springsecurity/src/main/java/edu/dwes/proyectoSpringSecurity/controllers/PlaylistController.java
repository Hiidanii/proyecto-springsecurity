/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.proyectoSpringSecurity.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.dwes.proyectoSpringSecurity.entities.Playlist;
import edu.dwes.proyectoSpringSecurity.services.PlaylistService;
import jakarta.validation.Valid;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author dani
 */
@Controller
@RequestMapping("/playlists")
public class PlaylistController {
    @Autowired
    private PlaylistService playlistService;

    // Listar todas las playlists
	@GetMapping("")
	public String listarPlaylists(Model model) {
		List<Playlist> playlists = playlistService.findAll();
		model.addAttribute("playlists", playlists);
		return "playlists/list";
	}

    // Buscar una playlist por su ID
    @GetMapping("/{id}") public String mostrarDetallesPlaylist(@PathVariable Long id, Model model) {
        Playlist playlist = playlistService.findById(id);
        if (playlist == null) {
            return "redirect:/playlists";
        }
        model.addAttribute("playlist", playlist);
        return "playlists/details";
    }

	// Mostrar formulario para crear una nueva playlist
	@GetMapping("/nueva")
	public String mostrarFormularioNuevaPlaylist(Model model) {
		model.addAttribute("playlist", new Playlist());
		return "playlists/form";
	}

	// Guardar una nueva playlist
	@PostMapping("/guardar")
	public String guardarPlaylist(@Valid @ModelAttribute("playlist") Playlist playlist, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "playlists/form";
		}
		playlistService.save(playlist);
		return "redirect:/playlists";
	}

	// Mostrar formulario para editar una playlist existente
	@GetMapping("/editar/{id}")
	public String mostrarFormularioEditarPlaylist(@PathVariable Long id, Model model) {
		Playlist playlist = playlistService.findById(id);
		if (playlist == null) {
			return "redirect:/playlists";
		}
		model.addAttribute("playlist", playlist);
		return "playlists/form";
	}

	// Eliminar una playlist
	@PostMapping("/eliminar/{id}")
	public String eliminarPlaylist(@PathVariable Long id, RedirectAttributes redirectAttrs) {
		try {
			playlistService.deleteById(id);
		} catch (RuntimeException e) {
			redirectAttrs.addFlashAttribute("error", e.getMessage());
		}
		return "redirect:/playlists";
	}
}
