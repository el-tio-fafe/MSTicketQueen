package cl.duoc.msDireccion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;

import cl.duoc.msDireccion.model.Comuna;
import java.util.Optional;


//@Repository
public interface ComunaRepository extends JpaRepository <Comuna, Integer>{

    Optional <Comuna> findByNombreComuna(String nombreComuna);

}
