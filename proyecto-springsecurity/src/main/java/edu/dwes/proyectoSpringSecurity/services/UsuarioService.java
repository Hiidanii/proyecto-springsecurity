package edu.dwes.proyectoSpringSecurity.services;

import edu.dwes.proyectoSpringSecurity.entities.Rol;
import edu.dwes.proyectoSpringSecurity.entities.Usuario;
import edu.dwes.proyectoSpringSecurity.repositories.RolRepository;
import edu.dwes.proyectoSpringSecurity.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario con ID " + id + " no encontrado"));
    }

    public List<Rol> findAllRoles() {
        return rolRepository.findAll();
    }

    // Registro público, asigna "ROLE_USER" automáticamente
    @Transactional
    public void registrar(Usuario usuario) {
        if (usuarioRepository.findByUsername(usuario.getUsername()).isPresent()) {
            throw new RuntimeException("El nombre de usuario '" + usuario.getUsername() + "' ya está en uso");
        }
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        Rol rolUser = rolRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Rol ROLE_USER no encontrado en la BD"));
        usuario.getRoles().add(rolUser);
        usuarioRepository.save(usuario);
    }

    // Creación por admin, permite asignar cualquier rol
    @Transactional
    public void guardar(Usuario usuario, List<Long> rolesIds) {
        if (usuario.getId() == null) {
            // Nuevo usuario: comprobar duplicado y encriptar
            if (usuarioRepository.findByUsername(usuario.getUsername()).isPresent()) {
                throw new RuntimeException("El nombre de usuario '" + usuario.getUsername() + "' ya está en uso");
            }
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }
        // Asignar los roles seleccionados
        List<Rol> roles = rolRepository.findAllById(rolesIds);
        usuario.setRoles(roles);
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void deleteById(Long id) {
        usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario con ID " + id + " no encontrado"));
        usuarioRepository.deleteById(id);
    }
}
