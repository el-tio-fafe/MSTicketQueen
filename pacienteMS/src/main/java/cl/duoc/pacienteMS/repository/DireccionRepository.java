package cl.duoc.pacienteMS.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.pacienteMS.model.Direccion;

@Repository
public interface DireccionRepository extends JpaRepository<Direccion, Integer> {

    Optional<Direccion> findByPacienteId(Integer pacienteId);

}