package edu.dwes.proyectoSpringSecurity.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PlaylistCancionId implements Serializable {

    @Column(name = "playlist_id")
    private Long playlistId;

    @Column(name = "cancion_id")
    private Long cancionId;

    public PlaylistCancionId() {}

    public PlaylistCancionId(Long playlistId, Long cancionId) {
        this.playlistId = playlistId;
        this.cancionId = cancionId;
    }

    public Long getPlaylistId() { return playlistId; }
    public void setPlaylistId(Long playlistId) { this.playlistId = playlistId; }
    public Long getCancionId() { return cancionId; }
    public void setCancionId(Long cancionId) { this.cancionId = cancionId; }

    // CORREGIDO: hashCode anterior siempre devolvía 3, rompiendo JPA y colecciones
    @Override
    public int hashCode() {
        return Objects.hash(playlistId, cancionId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PlaylistCancionId other = (PlaylistCancionId) obj;
        return Objects.equals(this.playlistId, other.playlistId)
            && Objects.equals(this.cancionId, other.cancionId);
    }
}
