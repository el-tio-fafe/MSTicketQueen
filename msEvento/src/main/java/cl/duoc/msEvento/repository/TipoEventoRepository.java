package cl.duoc.msEvento.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.msEvento.model.TipoEvento;

@Repository
public interface TipoEventoRepository extends JpaRepository<TipoEvento, Integer>{

    Optional<TipoEvento> findByDescripcion(String descripcion);

}
