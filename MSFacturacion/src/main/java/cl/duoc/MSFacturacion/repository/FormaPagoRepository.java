package cl.duoc.MSFacturacion.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.MSFacturacion.model.FormaPago;

@Repository
public interface FormaPagoRepository extends JpaRepository<FormaPago, Integer> {

    Optional<FormaPago> findByMedioDePago(String medioDePago);    

}
