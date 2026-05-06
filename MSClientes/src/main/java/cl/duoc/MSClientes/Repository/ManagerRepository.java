package cl.duoc.MSClientes.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.MSClientes.Model.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Integer> {

    Optional<Manager> findByRutMngr(String rutMngr);

    Optional<Manager> findByCorreoMngr(String correoMngr);

}
