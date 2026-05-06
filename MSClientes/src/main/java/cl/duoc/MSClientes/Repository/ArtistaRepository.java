package cl.duoc.MSClientes.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.MSClientes.Model.Artista;

public interface ArtistaRepository extends JpaRepository<Artista, Integer> {    
    Optional<Artista> findByRutArt(String rutArt);

    Optional<Artista> findByCorreoArt(String correoArt);

}
