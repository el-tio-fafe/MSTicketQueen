package cl.duoc.msEvento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.msEvento.model.Evento;
import java.util.List;
import java.util.Optional;


@Repository
public interface EventoRepository extends JpaRepository<Evento, Integer>{

    List<Evento> findByEstadoEvento(String estadoEvento);

    List<Evento> findByIdProd(Integer idProd);

    List<Evento> findByIdAdministrador(Integer idAdministrador);


    Optional<Evento> findByCodigoEvento(String codigoEvento);
}
