package cl.duoc.msVentaTicket.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.msVentaTicket.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer>{

    Optional<Ticket> findByCodigoQR(String codigoQR);

    List<Ticket> findByIdEvento(Integer idEvento);

    List<Ticket> findByIdAsiento(Integer idAsiento);

}

