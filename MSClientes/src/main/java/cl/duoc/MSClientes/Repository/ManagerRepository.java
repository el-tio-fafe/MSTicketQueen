package cl.duoc.MSClientes.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.MSClientes.Model.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Integer> {

}
