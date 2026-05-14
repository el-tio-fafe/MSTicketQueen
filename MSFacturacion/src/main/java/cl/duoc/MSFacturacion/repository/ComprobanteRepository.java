package cl.duoc.MSFacturacion.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.MSFacturacion.model.Comprobante;
import cl.duoc.MSFacturacion.model.banco;

@Repository
public interface ComprobanteRepository extends JpaRepository<Comprobante, Integer> {
    Optional<Comprobante> findByNumeroComprobante(String numeroComprobante); 
    Optional<Comprobante> findById(banco bancoExistente);
    List<Comprobante> findByBanco(banco bancoExistente);
}
