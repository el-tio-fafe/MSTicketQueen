package cl.duoc.msAsiento.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.msAsiento.model.Asiento;

@Repository
public interface AsientoRepository extends JpaRepository<Asiento, Integer>{

    Optional<Asiento> findByIdAsiento(Integer idAsiento);

    Optional<Asiento> findByNumeroAsiento(String numeroAsiento);
    
    public void eliminarPorNumeroAsiento(String numeroAsiento);


}
