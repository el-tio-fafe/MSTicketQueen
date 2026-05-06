package cl.duoc.MSFacturacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.MSFacturacion.model.banco;

@Repository
public interface bancoRepository extends JpaRepository<banco, Integer> {

}
