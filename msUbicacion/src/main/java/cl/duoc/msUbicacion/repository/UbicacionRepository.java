package cl.duoc.msUbicacion.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.msUbicacion.model.Ubicacion;

@Repository
public interface UbicacionRepository extends JpaRepository<Ubicacion, Integer> {

    Optional<Ubicacion> findByNombreUbi(String nombreUbi);

    // buscar ubicaciones con stock mayor a 0
    List<Ubicacion> findByStockDisponibleUbiGreaterThan(Integer stock);
}
