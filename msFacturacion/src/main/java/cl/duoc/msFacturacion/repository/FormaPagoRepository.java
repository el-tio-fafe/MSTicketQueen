package cl.duoc.msFacturacion.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.msFacturacion.model.FormaPago;

@Repository
public interface FormaPagoRepository extends JpaRepository<FormaPago, Integer> {

    Optional<FormaPago> findByMedioDePago(String medioDePago);    

}
