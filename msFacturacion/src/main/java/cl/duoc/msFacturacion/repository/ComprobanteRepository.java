package cl.duoc.MSFacturacion.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.MSFacturacion.model.Comprobante;
import cl.duoc.MSFacturacion.model.Banco;

@Repository
public interface ComprobanteRepository extends JpaRepository<Comprobante, Integer> {

    Optional<Comprobante> findByNumeroComprobante(String numeroComprobante); 
    
    List<Comprobante> findByBanco(Banco banco);

    List<Comprobante> findByBancoAndFechaEmision(Banco banco, LocalDateTime fechaEmision);
}
