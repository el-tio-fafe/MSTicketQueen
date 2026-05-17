package cl.duoc.msAdministrador.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.msAdministrador.model.Administrador;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Integer> {

    Optional<Administrador> findByRutAdm(String rutAdm);

    Optional<Administrador> findByCorreoAdm(String correoAdm);

    boolean existsByRutAdm(String rutAdm);
    
}
