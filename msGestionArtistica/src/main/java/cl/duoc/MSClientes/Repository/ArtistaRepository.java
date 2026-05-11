package cl.duoc.msGestionArtistica.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.msGestionArtistica.Model.Artista;

public interface ArtistaRepository extends JpaRepository<Artista, Integer> {    
    Optional<Artista> findByRutArt(String rutArt);

    Optional<Artista> findByCorreoArt(String correoArt);

}
