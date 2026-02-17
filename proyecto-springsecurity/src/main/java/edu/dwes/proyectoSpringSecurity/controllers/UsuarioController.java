package edu.dwes.proyectoSpringSecurity.controllers;

import edu.dwes.proyectoSpringSecurity.entities.Usuario;
import edu.dwes.proyectoSpringSecurity.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Lista todos los usuarios, solo admin
    @GetMapping("")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.findAll());
        return "usuarios/list";
    }

    // Formulario de registro público, asigna "ROLE_USER"
    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuarios/registro";
    }

    @PostMapping("/registro")
    public String registrar(@Valid @ModelAttribute("usuario") Usuario usuario,
                            BindingResult result,
                            RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            return "usuarios/registro";
        }
        try {
            usuarioService.registrar(usuario);
            redirectAttrs.addFlashAttribute("success", "Registro exitoso. Ya puedes iniciar sesión.");
            return "redirect:/login";
        } catch (RuntimeException e) {
            result.rejectValue("username", "error.usuario", e.getMessage());
            return "usuarios/registro";
        }
    }

    // Formulario de creación por admin, permite asignar roles
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("roles", usuarioService.findAllRoles());
        return "usuarios/form";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("usuario") Usuario usuario,
                          BindingResult result,
                          @RequestParam(value = "rolesIds", required = false) List<Long> rolesIds,
                          Model model,
                          RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            model.addAttribute("roles", usuarioService.findAllRoles());
            return "usuarios/form";
        }
        if (rolesIds == null || rolesIds.isEmpty()) {
            model.addAttribute("roles", usuarioService.findAllRoles());
            model.addAttribute("errorRoles", "Debes asignar al menos un rol");
            return "usuarios/form";
        }
        try {
            usuarioService.guardar(usuario, rolesIds);
            redirectAttrs.addFlashAttribute("success", "Usuario guardado correctamente");
        } catch (RuntimeException e) {
            result.rejectValue("username", "error.usuario", e.getMessage());
            model.addAttribute("roles", usuarioService.findAllRoles());
            return "usuarios/form";
        }
        return "redirect:/usuarios";
    }

    // Eliminar usuario, solo admin
    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        try {
            usuarioService.deleteById(id);
        } catch (RuntimeException e) {
            redirectAttrs.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/usuarios";
    }

    // Página de acceso denegado
    @GetMapping("/errorNoAutorizado")
    public String errorNoAutorizado() {
        return "usuarios/errorNoAutorizado";
    }
}
