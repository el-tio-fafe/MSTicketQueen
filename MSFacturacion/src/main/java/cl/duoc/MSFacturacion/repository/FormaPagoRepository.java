package cl.duoc.MSFacturacion.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.MSFacturacion.model.Formapago;

@Repository
public interface FormaPagoRepository extends JpaRepository<Formapago, Integer> {

    Optional<Formapago> findByMedioDePago(String medioDePago);    

}
