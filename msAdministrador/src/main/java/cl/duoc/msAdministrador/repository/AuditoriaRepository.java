package cl.duoc.msAdministrador.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.msAdministrador.model.Administrador;
import cl.duoc.msAdministrador.model.Auditoria;

@Repository
public interface AuditoriaRepository extends JpaRepository<Auditoria, Integer>   {

    Optional<List<Auditoria>> findByAdministrador(Administrador administrador);

     List<Auditoria> findByAdministrador_RutAdm(String rutAdm);

}
