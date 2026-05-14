package cl.duoc.MSFacturacion.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.MSFacturacion.model.banco;

@Repository
public interface bancoRepository extends JpaRepository<banco, Integer> {
    Optional<banco> findByNombre(String nombre);
    Optional<banco> findById(Integer id);
}
