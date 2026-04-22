package cl.duoc.pacienteMS.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.pacienteMS.model.Contacto;

@Repository
public interface ContactoRepository extends JpaRepository<Contacto, Integer> {

    Optional<Contacto> findByPacienteId(Integer pacienteId);

}