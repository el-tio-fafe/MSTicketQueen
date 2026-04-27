package cl.duoc.msAsiento.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.msAsiento.model.ReservaTemporal;

@Repository
public interface ReservaTemporalRepository extends JpaRepository<ReservaTemporal, Integer>{

    Optional<ReservaTemporal> findByIdReserva(Integer idReserva);

}
 