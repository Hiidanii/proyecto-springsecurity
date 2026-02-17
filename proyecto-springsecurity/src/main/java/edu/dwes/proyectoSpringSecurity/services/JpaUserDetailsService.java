package edu.dwes.proyectoSpringSecurity.services;

import edu.dwes.proyectoSpringSecurity.entities.Usuario;
import edu.dwes.proyectoSpringSecurity.repositories.UsuarioRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca el usuario en la BD. Si no existe, Spring Security rechaza el login automáticamente.
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        return new org.springframework.security.core.userdetails.User(
                usuario.getUsername(),
                usuario.getPassword(),
                usuario.isEnabled(),
                true, // accountNonExpired
                true, // credentialsNonExpired
                true, // accountNonLocked
                getAuthorities(usuario)
        );
    }

    private List<GrantedAuthority> getAuthorities(Usuario usuario) {
        return usuario.getRoles().stream()
                .map(rol -> new SimpleGrantedAuthority(rol.getName()))
                .collect(Collectors.toList());
    }
}
