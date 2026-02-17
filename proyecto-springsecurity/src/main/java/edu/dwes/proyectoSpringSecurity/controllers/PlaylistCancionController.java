package edu.dwes.proyectoSpringSecurity.controllers;

import edu.dwes.proyectoSpringSecurity.entities.PlaylistCancion;
import edu.dwes.proyectoSpringSecurity.entities.PlaylistCancionId;
import edu.dwes.proyectoSpringSecurity.services.CancionService;
import edu.dwes.proyectoSpringSecurity.services.PlaylistCancionService;
import edu.dwes.proyectoSpringSecurity.services.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/playlists-canciones")
public class PlaylistCancionController {

    @Autowired
    private PlaylistCancionService playlistCancionService;

    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private CancionService cancionService;

    // Listar todas las relaciones Playlist-Cancion
    @GetMapping("")
    public String listarRelaciones(Model model) {
        List<PlaylistCancion> relaciones = playlistCancionService.findAll();
        model.addAttribute("relaciones", relaciones);
        return "playlists-canciones/list";
    }

    // Ver detalles de una relación por su ID compuesto
    @GetMapping("/{playlistId}/{cancionId}")
    public String mostrarDetallesRelacion(@PathVariable Long playlistId,
                                          @PathVariable Long cancionId,
                                          Model model,
                                          RedirectAttributes redirectAttrs) {
        PlaylistCancionId id = new PlaylistCancionId(playlistId, cancionId);
        try {
            PlaylistCancion relacion = playlistCancionService.findById(id);
            model.addAttribute("relacion", relacion);
            return "playlists-canciones/details";
        } catch (RuntimeException e) {
            redirectAttrs.addFlashAttribute("error", e.getMessage());
            return "redirect:/playlists-canciones";
        }
    }

    // Mostrar formulario para crear una nueva relación
    @GetMapping("/nueva")
    public String mostrarFormularioNuevaRelacion(Model model) {
        model.addAttribute("playlists", playlistService.findAll());
        model.addAttribute("canciones", cancionService.findAll());
        return "playlists-canciones/form";
    }

    // Guardar una nueva relación (posición autoincremental en el servicio)
    @PostMapping("/guardar")
    public String guardarRelacion(@RequestParam Long playlistId,
                                  @RequestParam Long cancionId,
                                  Model model,
                                  RedirectAttributes redirectAttrs) {
        try {
            playlistCancionService.save(playlistId, cancionId);
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("playlists", playlistService.findAll());
            model.addAttribute("canciones", cancionService.findAll());
            return "playlists-canciones/form";
        }
        return "redirect:/playlists-canciones";
    }

    // Eliminar una relación - DEBE ser POST (nunca GET)
    @PostMapping("/eliminar/{playlistId}/{cancionId}")
    public String eliminarRelacion(@PathVariable Long playlistId,
                                   @PathVariable Long cancionId,
                                   RedirectAttributes redirectAttrs) {
        PlaylistCancionId id = new PlaylistCancionId(playlistId, cancionId);
        try {
            playlistCancionService.deleteById(id);
        } catch (RuntimeException e) {
            redirectAttrs.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/playlists-canciones";
    }
}
