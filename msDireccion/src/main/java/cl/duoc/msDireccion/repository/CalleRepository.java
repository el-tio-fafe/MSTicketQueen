package cl.duoc.msDireccion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.msDireccion.model.Calle;

@Repository
public interface CalleRepository extends JpaRepository<Calle, Integer> {

    public Calle findByNombreCalle(String nombreCalle);

    List<Calle> findByComuna_IdComuna(Integer idComuna);
}
