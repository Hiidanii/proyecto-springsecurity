package edu.dwes.proyectoSpringSecurity.security;

import edu.dwes.proyectoSpringSecurity.services.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SpringSecurityConfig {

    @Autowired
    private JpaUserDetailsService jpaUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .userDetailsService(jpaUserDetailsService)
            .authorizeHttpRequests(authz -> authz
                // Acceso público: página principal, registro, recursos estáticos
                .requestMatchers("/", "/usuarios/registro", "/css/**", "/style.css").permitAll()

                // Solo el admin puede gestionar usuarios
                .requestMatchers("/usuarios/**").hasAuthority("ROLE_ADMIN")

                // Solo el admin puede crear, editar y eliminar canciones
                .requestMatchers("/canciones/nueva", "/canciones/guardar",
                                 "/canciones/editar/**", "/canciones/eliminar/**").hasAuthority("ROLE_ADMIN")

                // Solo el admin puede crear, editar y eliminar playlists
                .requestMatchers("/playlists/nueva", "/playlists/guardar",
                                 "/playlists/editar/**", "/playlists/eliminar/**").hasAuthority("ROLE_ADMIN")

                // Solo el amdin puede gestionar relaciones playlist-canción
                .requestMatchers("/playlists-canciones/nueva", "/playlists-canciones/guardar",
                                 "/playlists-canciones/eliminar/**").hasAuthority("ROLE_ADMIN")

                // Ver listas y detalles: cualquier usuario autenticado
                .anyRequest().authenticated()
            )
            .formLogin(login -> login
                .loginPage("/login")               // Página de login personalizada
                .defaultSuccessUrl("/", true)      // Tras login exitoso va al inicio
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .exceptionHandling(ex -> ex
                .accessDeniedPage("/usuarios/errorNoAutorizado")
            );

        return http.build();
    }
}
