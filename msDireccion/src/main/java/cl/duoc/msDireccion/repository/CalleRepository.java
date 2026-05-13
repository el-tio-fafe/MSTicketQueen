package cl.duoc.msDireccion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.msDireccion.model.Calle;

@Repository
public interface CalleRepository extends JpaRepository<Calle, Integer> {

    public Calle findByNombre(String nombre);
}
