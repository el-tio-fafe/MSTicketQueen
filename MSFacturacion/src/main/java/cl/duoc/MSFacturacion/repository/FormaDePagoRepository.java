package cl.duoc.MSFacturacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.MSFacturacion.model.formaPago;

@Repository
public interface FormaDePagoRepository extends JpaRepository<formaPago, Integer> {

}
