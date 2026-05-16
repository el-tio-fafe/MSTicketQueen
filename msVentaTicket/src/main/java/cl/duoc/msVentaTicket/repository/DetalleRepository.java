package cl.duoc.msVentaTicket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.msVentaTicket.model.Detalle;

@Repository
public interface DetalleRepository extends JpaRepository<Detalle, Integer>{

    List<Detalle> findByTicket_IdEvento(Integer idEvento);

}
