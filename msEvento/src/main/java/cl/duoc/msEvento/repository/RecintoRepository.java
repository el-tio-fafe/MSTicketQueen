package cl.duoc.msEvento.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.msEvento.model.Recinto;

@Repository
public interface RecintoRepository extends JpaRepository<Recinto, Integer>{

    Optional<Recinto> findByNombreRecinto(String nombreRecinto);

    Optional<Recinto> findByRutRecinto(String rutRecinto);

}
