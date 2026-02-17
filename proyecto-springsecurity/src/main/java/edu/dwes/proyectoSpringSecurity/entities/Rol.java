package edu.dwes.proyectoSpringSecurity.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.*;

@Entity
@Table(name = "roles")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Spring Security exige el formato ROLE_XXXX
    @NotBlank(message = "El nombre del rol es obligatorio")
    @Column(unique = true, nullable = false, length = 30)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<Usuario> usuarios = new ArrayList<>();

    public Rol() {}
    public Rol(String name) { this.name = name; }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Objects.equals(id, ((Rol) o).id);
    }

    @Override
    public String toString() { return name; }
}
