CREATE DATABASE gestion_musical CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE gestion_musical;

CREATE TABLE canciones (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(50) NOT NULL,
    artista VARCHAR(20) NOT NULL,
    duracion DOUBLE NOT NULL,
    genero VARCHAR(20) NOT NULL
);

CREATE TABLE playlists (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    descripcion VARCHAR(200) NOT NULL,
    fecha_creacion DATE NOT NULL
);

CREATE TABLE playlists_canciones (
    playlist_id BIGINT NOT NULL,
    cancion_id BIGINT NOT NULL,
    posicion INT NOT NULL,
    PRIMARY KEY (playlist_id, cancion_id),
    FOREIGN KEY (playlist_id) REFERENCES playlists(id),
    FOREIGN KEY (cancion_id) REFERENCES canciones(id)
);

CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(30) NOT NULL UNIQUE
);

CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    enabled TINYINT(1) NOT NULL DEFAULT 1
);

CREATE TABLE usuarios_roles (
    usuario_id BIGINT NOT NULL,
    rol_id BIGINT NOT NULL,
    PRIMARY KEY (usuario_id, rol_id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    FOREIGN KEY (rol_id) REFERENCES roles(id)
);

INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_MODERATOR');

INSERT INTO usuarios (username, password, enabled) VALUES ('admin', '$2a$10$LLMECz2S2C4u.eF1e3FyueHvP/g8jir31DcJwVji9pJwEwQ5W4z3S', 1);
INSERT INTO usuarios (username, password, enabled) VALUES ('usuario', '$2a$10$mlTBBgQX0RyAd.pp40W.sulphJ2JvcWrZH3XXm2rg1zDDkbpumXGm', 1);

INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES (1, 1);
INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES (2, 2);

INSERT INTO canciones (titulo, artista, duracion, genero) VALUES ('Runaway', 'Kanye West', 9.08, 'Hip-Hop');
INSERT INTO canciones (titulo, artista, duracion, genero) VALUES ('Alright', 'Kendrick Lamar', 3.39, 'Hip-Hop');
INSERT INTO canciones (titulo, artista, duracion, genero) VALUES ('Nights', 'Frank Ocean', 5.07, 'R&B');
INSERT INTO canciones (titulo, artista, duracion, genero) VALUES ('God Is', 'Kanye West', 3.23, 'Gospel');
INSERT INTO canciones (titulo, artista, duracion, genero) VALUES ('Ivy', 'Frank Ocean', 4.09, 'R&B');
INSERT INTO canciones (titulo, artista, duracion, genero) VALUES ('DNA.', 'Kendrick Lamar', 3.06, 'Hip-Hop');
INSERT INTO canciones (titulo, artista, duracion, genero) VALUES ('Ultralight Beam', 'Kanye West', 5.20, 'Hip-Hop');
INSERT INTO canciones (titulo, artista, duracion, genero) VALUES ('Pink + White', 'Frank Ocean', 3.04, 'R&B');
INSERT INTO canciones (titulo, artista, duracion, genero) VALUES ('HUMBLE.', 'Kendrick Lamar', 2.57, 'Hip-Hop');

INSERT INTO playlists (nombre, descripcion, fecha_creacion) VALUES ('Kanye Essentials', 'Mejores canciones de Kanye West', '2024-01-01');
INSERT INTO playlists (nombre, descripcion, fecha_creacion) VALUES ('Hip-Hop Moderno', 'Hits de Kendrick, Kanye y Frank Ocean', '2024-01-10');
INSERT INTO playlists (nombre, descripcion, fecha_creacion) VALUES ('Frank Ocean Vibes', 'Canciones top de Frank Ocean', '2024-01-15');
INSERT INTO playlists (nombre, descripcion, fecha_creacion) VALUES ('Kendrick Lamar Power', 'Lo mejor de Kendrick Lamar', '2024-01-20');

INSERT INTO playlists_canciones (playlist_id, cancion_id, posicion) VALUES (1, 1, 1);
INSERT INTO playlists_canciones (playlist_id, cancion_id, posicion) VALUES (1, 4, 2);
INSERT INTO playlists_canciones (playlist_id, cancion_id, posicion) VALUES (1, 7, 3);

INSERT INTO playlists_canciones (playlist_id, cancion_id, posicion) VALUES (2, 2, 1);
INSERT INTO playlists_canciones (playlist_id, cancion_id, posicion) VALUES (2, 6, 2);
INSERT INTO playlists_canciones (playlist_id, cancion_id, posicion) VALUES (2, 9, 3);
INSERT INTO playlists_canciones (playlist_id, cancion_id, posicion) VALUES (2, 1, 4);
INSERT INTO playlists_canciones (playlist_id, cancion_id, posicion) VALUES (2, 3, 5);

INSERT INTO playlists_canciones (playlist_id, cancion_id, posicion) VALUES (3, 3, 1);
INSERT INTO playlists_canciones (playlist_id, cancion_id, posicion) VALUES (3, 5, 2);
INSERT INTO playlists_canciones (playlist_id, cancion_id, posicion) VALUES (3, 8, 3);

INSERT INTO playlists_canciones (playlist_id, cancion_id, posicion) VALUES (4, 2, 1);
INSERT INTO playlists_canciones (playlist_id, cancion_id, posicion) VALUES (4, 6, 2);
INSERT INTO playlists_canciones (playlist_id, cancion_id, posicion) VALUES (4, 9, 3);