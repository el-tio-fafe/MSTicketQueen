package cl.duoc.MSFacturacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.MSFacturacion.model.Comprobante;

@Repository
public interface ComprobanteRepository extends JpaRepository<Comprobante, Integer> {

}
