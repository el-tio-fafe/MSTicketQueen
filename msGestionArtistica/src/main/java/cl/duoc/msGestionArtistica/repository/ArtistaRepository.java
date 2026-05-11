package cl.duoc.msGestionArtistica.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.msGestionArtistica.model.Artista;

public interface ArtistaRepository extends JpaRepository<Artista, Integer> {    
    Optional<Artista> findByRutArt(String rutArt);

    Optional<Artista> findByCorreoArt(String correoArt);


}
