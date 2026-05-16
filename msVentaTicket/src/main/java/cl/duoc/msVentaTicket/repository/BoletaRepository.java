package cl.duoc.msVentaTicket.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.msVentaTicket.model.Boleta;

@Repository
public interface BoletaRepository extends JpaRepository<Boleta, Integer>{

    Optional<Boleta> findByNumeroBoleta(Integer numeroBoleta);

    List<Boleta> findByIdComprador(Integer idComprador);

}
